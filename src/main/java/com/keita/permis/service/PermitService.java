package com.keita.permis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.keita.permis.dto.RequestPermitForm;
import com.keita.permis.enums.PermitCategory;
import com.keita.permis.enums.PermitType;
import com.keita.permis.model.Citizen;
import com.keita.permis.model.Permit;
import com.keita.permis.repository.CitizenRepository;
import com.keita.permis.repository.PermitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PermitService {

    @Autowired
    private PermitRepository permitRepository;

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private JavaMailSender javaMailSender;

    private final Logger logger = LoggerFactory.getLogger(PermitService.class);

    public boolean generatePermit(String email) throws Exception {
        Optional<Citizen> citizenOptional = citizenRepository.findByEmail(email);

        logger.info(email);
        if (citizenOptional.isPresent()) {
            Optional<Permit> permitOptionalActive = permitRepository.findByActiveTrueAndCitizenEmail(email);
            int nbrPermitOfThisCitizen = permitRepository.countByCitizenEmail(email);

            if (permitOptionalActive.isEmpty() && nbrPermitOfThisCitizen > 0) {
                return false;
            }
            if (permitOptionalActive.isEmpty() && nbrPermitOfThisCitizen == 0) {
                savePermit(citizenOptional.get());
            }

            List<Path> filePaths = setListPath(citizenOptional.get().getLastName());

            return generateQR(citizenOptional.get(), filePaths.get(0)) &&
                    generatePDF(citizenOptional.get(), filePaths.get(0), filePaths.get(1)) &&
                    sendPermitToCitizen(citizenOptional.get(), filePaths);
        }
        return false;
    }

    public boolean renewPermit(Citizen citizen) throws Exception {
        Optional<Permit> permitOptionalActive = permitRepository.findByActiveTrueAndCitizenEmail(citizen.getEmail());
        int nbrPermitOfThisCitizen = permitRepository.countByCitizenEmail(citizen.getEmail());

        if (permitOptionalActive.isEmpty() && nbrPermitOfThisCitizen > 0) {
            savePermit(citizen);

            List<Path> filePaths = setListPath(citizen.getLastName());

            return generateQR(citizen, filePaths.get(0)) &&
                    generatePDF(citizen, filePaths.get(0), filePaths.get(1)) &&
                    sendPermitToCitizen(citizen, filePaths);
        }
        return false;

    }

    //TODO : test and maybe move it into PoolService
    public Citizen getBySocialInsuranceFromMinistry(String socialInsurance) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity =
                restTemplate
                        .getForEntity(environment.getProperty("api.url.renewal") +
                                socialInsurance, String.class);
        if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty())
            return mapper.readValue(responseEntity.getBody(), Citizen.class);
        return null;
    }

    private void savePermit(Citizen citizen) {
        int ageOfCitizen = getYearsBetweenNowAndThen(citizen.getDateOfBirth());
        PermitCategory permitCategory = PermitCategory.determinePermitCategory(ageOfCitizen);
        PermitType permitType = citizen.isVaccinated() ? PermitType.VACCINE : PermitType.TEST;
        Permit permit = Permit.builder()
                .citizen(citizen).permitCategory(permitCategory).permitType(permitType).build();
        permitRepository.save(permit);
    }

    private int getYearsBetweenNowAndThen(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    private List<Path> setListPath(String fileName) {
        Path qrFilePath = FileSystems.getDefault().getPath(
                environment.getProperty("qr.directory") +
                        fileName +
                        environment.getProperty("qrcode.extension"));

        Path pdfFilePath = FileSystems.getDefault().getPath(
                environment.getProperty("pdf.directory") +
                        fileName +
                        environment.getProperty("pdf.extension"));
        return Arrays.asList(qrFilePath, pdfFilePath);
    }

    private boolean generateQR(Citizen citizen, Path path) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        MatrixToImageWriter
                .writeToPath(qrCodeWriter.encode(
                        citizen.getSocialInsurance(),
                        BarcodeFormat.QR_CODE,
                        Integer.parseInt(Objects.requireNonNull(environment.getProperty("qrcode.dimension"))),
                        Integer.parseInt(Objects.requireNonNull(environment.getProperty("qrcode.dimension")))),
                        Objects.requireNonNull(environment.getProperty("qrcode.format")),
                        path);
        return Files.exists(path);
    }

    private boolean generatePDF(Citizen citizen, Path qrFilePath, Path pdfFilePath) throws Exception {
        if (Files.exists(qrFilePath)) {
            PdfWriter pdfWriter = new PdfWriter(pdfFilePath.toString());
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);
            Image image = new Image(ImageDataFactory.create(qrFilePath.toString()));
            Paragraph paragraph = new Paragraph("Hi M/Mme." + citizen.getLastName() + ", here is your qrCode and please be safe!\n")
                    .add(image);
            document.add(paragraph);
            document.close();
        }
        return Files.exists(pdfFilePath);
    }

    private boolean sendPermitToCitizen(Citizen citizen, List<Path> filePaths) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        if (getYearsBetweenNowAndThen(citizen.getDateOfBirth()) < Integer.parseInt(Objects.requireNonNull(environment.getProperty("age.min"))))
            helper.setTo(citizen.getParent().getEmail());
        else
            helper.setTo(citizen.getEmail());

        helper.setSubject("PERMIT");
        helper.setText("FORMAT PNG et PDF", true);
        helper.addAttachment("QR_CODE IMAGE", new File(filePaths.get(0).toString()));
        helper.addAttachment("QR_CODE PDF", new File(filePaths.get(1).toString()));
        javaMailSender.send(helper.getMimeMessage());
        return deleteFile(filePaths);
    }

    private boolean deleteFile(List<Path> paths) throws IOException {
        for (Path path : paths)
            if (!Files.deleteIfExists(path))
                return false;
        return true;

    }

}
