package com.keita.permis.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.keita.permis.model.Citizen;
import com.keita.permis.repository.PermitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

@Service
public class PermitService {
    //TODO: remove all static field after making sure that everything works

    @Autowired
    private PermitRepository permitRepository;

    @Autowired
    private static Environment environment;

    @Autowired
    private static JavaMailSender javaMailSender;


    //TODO: refactoring(try and catch + bitMatrix + write + save Permit)
    public static void generatePermit(Citizen citizen, String filePath) throws WriterException, IOException {
        Path path = FileSystems.getDefault().getPath(filePath);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix =  qrCodeWriter.encode(
                citizen.getSocialInsurance(),
                BarcodeFormat.QR_CODE,
                Integer.parseInt(environment.getProperty("QrCode.width")),
                Integer.parseInt(environment.getProperty("QrCode.heigth")));

        MatrixToImageWriter
                .writeToPath(bitMatrix,
                        environment.getProperty("QrCode.extension"),
                        path);
    }

    //TODO:
    //      *refactoring put qrCode as an argument
    //      *save qrCode as byte[]
    public static void generatePDF(String filePath, String qrFilePath) throws Exception {
        PdfWriter pdfWriter = new PdfWriter(filePath);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);

        Document document = new Document(pdfDocument);
        Image image = new Image(ImageDataFactory.create(qrFilePath));
        Paragraph paragraph = new Paragraph("Good morning fellow citizen, here is your qrCode\n")
                .add(image);
        document.add(paragraph);
        document.close();
    }

    //TODO: replace citizen by a generic to be able to use it for reset

    public static void sendEmail(Citizen citizen, List<String> filePaths) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(citizen.getEmail());
        helper.addAttachment("QR_CODE IMAGE", new File(filePaths.get(0)));
        helper.addAttachment("QR_CODE PDF", new File(filePaths.get(1)));
        javaMailSender.send(helper.getMimeMessage());
    }


    public static void main(String[] args) throws Exception {
        //Atring qrFilePath = environment.getProperty("Files.qrFilePath") + "firstQr.PNG";
        //String pdfFilePath = environment.getProperty("Files.qrFilePath") + "firstPDf.pdf";
        Citizen citizen = Citizen.builder()
                .socialInsurance("92786327")
                .build();
       // generatePermit(citizen,qrFilePath);
        //generatePDF(pdfFilePath,qrFilePath);

    }
}
