package com.keita.permis.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.keita.permis.dto.SubmitForm;
import com.keita.permis.enums.PermitCategory;
import com.keita.permis.enums.PermitType;
import com.keita.permis.model.Citizen;
import com.keita.permis.model.Permit;
import com.keita.permis.repository.CitizenRepository;
import com.keita.permis.repository.PermitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class PermitService {

    @Autowired
    private PermitRepository permitRepository;

    @Autowired
    private CitizenRepository citizenRepository;

    //Put this as environment properties
    private final String qrDirectory = "C:\\Users\\mansa\\Documents\\OneDrive\\Documents\\techniqueInformatique\\quatriemeSession\\spring-angular\\PermisSante\\barCode\\";
    private final String pdfDirectory = "C:\\Users\\mansa\\Documents\\OneDrive\\Documents\\techniqueInformatique\\quatriemeSession\\spring-angular\\PermisSante\\pdf\\";

    public boolean generatePermit(SubmitForm submitForm) {
        Optional<Citizen> citizenOptional = citizenRepository.findByEmailAndPassword(submitForm.getEmail(), submitForm.getPassword());
        Optional<Permit> permitOptional = permitRepository.findByActiveTrueAndCitizenEmail(submitForm.getEmail());
        int nbrPermitOfThisCitizen = permitRepository.countByCitizenEmail(submitForm.getEmail());

        if (citizenOptional.isPresent()) {
            if (permitOptional.isEmpty() && nbrPermitOfThisCitizen > 0) {
                return false;
            }
            if (permitOptional.isEmpty()) {
                savePermit( citizenOptional.get());
            }
            //TODO : generateQR/generatePDF/sendEmail
            return true;
        }

        return false;
        /*
        if (!citizen.getLastName().isEmpty()) {

            Path qrDirectoryPath = FileSystems.getDefault().getPath(qrDirectory);
            Path pdfDirectoryPath = FileSystems.getDefault().getPath(pdfDirectory);
            Path qrFilePath = FileSystems.getDefault().getPath(qrDirectory + citizen.getLastName() + ".PNG");
            Path pdfFilePath = FileSystems.getDefault().getPath(pdfDirectory + citizen.getLastName() + ".pdf");

            if (Files.exists(qrDirectoryPath) && Files.exists(pdfDirectoryPath)) {

                if (generateQR(citizen, qrFilePath)) {
                    return generatePDF(citizen);
                }
            }
        }
         */
    }

    private void savePermit(Citizen citizen) {
        int ageOfCitizen = getYearsBetweenNowAndThen(citizen.getDateOfBirth());
        PermitCategory permitCategory = PermitCategory.determinePermitCategory(ageOfCitizen);
        PermitType permitType = citizen.isVaccinated() ? PermitType.VACCINE : PermitType.TEST;
        Permit permit =
                Permit.builder()
                        .restrictedAreas("")
                        .citizen(citizen).permitCategory(permitCategory).permitType(permitType).build();
        permitRepository.save(permit);
    }

    private int getYearsBetweenNowAndThen(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
            /*

    //TODO: put width,height,format as environment properties
    private boolean generateQR(Citizen citizen, Path path) {
        if (!citizen.getSocialInsurance().isEmpty()) {

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            try {
                MatrixToImageWriter
                        .writeToPath(qrCodeWriter.encode(
                                citizen.getSocialInsurance(),
                                BarcodeFormat.QR_CODE,
                                300,
                                300),
                                "PNG",
                                path);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean generatePDF(Citizen citizen) {
        String pdfFile = pdfDirectory + citizen.getLastName() + ".pdf";
        String qrFile = qrDirectory + citizen.getLastName() + ".PNG";

        try {
            PdfWriter pdfWriter = new PdfWriter(pdfFile);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);
            Image image = new Image(ImageDataFactory.create(qrFile));
            Paragraph paragraph = new Paragraph("Hi M/Mme." + citizen.getLastName() + ", here is your qrCode and please be safe!\n")
                    .add(image);
            document.add(paragraph);
            document.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //TODO : call into sendPermitToCitizen
    private boolean deleteFile(List<Path> paths) {
        for (Path path : paths) {
            if (Files.exists(path)) {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return true;
    }

    //TODO: replace citizen by a generic to be able to use it for reset
    /*
    public static void sendPermitToCitizen(Citizen citizen, List<String> filePaths) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(citizen.getEmail());
        helper.addAttachment("QR_CODE IMAGE", new File(filePaths.get(0)));
        helper.addAttachment("QR_CODE PDF", new File(filePaths.get(1)));
        javaMailSender.send(helper.getMimeMessage());
    }
     */

}
