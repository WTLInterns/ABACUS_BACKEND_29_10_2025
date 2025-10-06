package com.example.Abacus.Service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class CertificateService {

    public String generateCertificate(String name) {
        try {
            String fileName = System.getProperty("user.dir") + "/certificate_" + name + ".pdf";

            Document document = new Document(PageSize.A4.rotate());
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            
            PdfContentByte canvas = writer.getDirectContent();

            canvas.setColorFill(new BaseColor(245, 245, 245));
            canvas.rectangle(0, 0, document.getPageSize().getWidth(), document.getPageSize().getHeight());
            canvas.fill();

           
            float width = document.getPageSize().getWidth();
            float height = document.getPageSize().getHeight();

            CMYKColor pink1 = new CMYKColor(0.12f, 0.94f, 0.32f, 0.04f);
            CMYKColor pink2 = new CMYKColor(0.25f, 0.92f, 0.17f, 0.05f);
            PdfShading shading = PdfShading.simpleAxial(writer, 0, 0, 0, height, pink1, pink2);
            PdfShadingPattern pattern = new PdfShadingPattern(shading);

            canvas.setColorFill(new BaseColor(200, 0, 100));
            canvas.moveTo(width - 150, 0);
            canvas.curveTo(width - 200, height * 0.2f, width - 50, height * 0.8f, width - 100, height);
            canvas.lineTo(width, height);
            canvas.lineTo(width, 0);
            canvas.closePathFillStroke();

            canvas.setColorFill(new BaseColor(100, 0, 50));
            canvas.moveTo(width - 160, 0);
            canvas.curveTo(width - 210, height * 0.25f, width - 60, height * 0.75f, width - 110, height);
            canvas.lineTo(width, height);
            canvas.lineTo(width, 0);
            canvas.closePathFillStroke();

            canvas.setColorFill(new BaseColor(50, 50, 50));
            canvas.rectangle(width - 220, 0, 220, height);
            canvas.fill();

            canvas.setColorStroke(new BaseColor(150, 150, 150));
            canvas.setLineWidth(3);
            canvas.rectangle(15, 15, document.getPageSize().getWidth() - 30, document.getPageSize().getHeight() - 30);
            canvas.stroke();
            
            Font fontTitle = FontFactory.getFont("Montserrat-Bold", BaseFont.IDENTITY_H, true, 48, Font.NORMAL, new BaseColor(255, 0, 0));
            Paragraph title = new Paragraph("CERTIFICATE", fontTitle);
            title.setIndentationLeft(30);
            document.add(title);
            
            fontTitle.setSize(30);
            Paragraph subtitle = new Paragraph("OF ACHIEVEMENT", fontTitle);
            subtitle.setIndentationLeft(30);
            document.add(subtitle);
            
            Font fontProudly = FontFactory.getFont("Montserrat-SemiBold", BaseFont.IDENTITY_H, true, 24, Font.NORMAL, new BaseColor(50, 50, 50));
            Paragraph proudly = new Paragraph("PROUDLY PRESENTED TO", fontProudly);
            proudly.setSpacingBefore(30);
            proudly.setIndentationLeft(30);
            document.add(proudly);

            Font fontName = FontFactory.getFont("DancingScript-Regular", BaseFont.IDENTITY_H, true, 60, Font.NORMAL, new BaseColor(180, 0, 100));
            Paragraph namePara = new Paragraph(name, fontName);
            namePara.setIndentationLeft(30);
            document.add(namePara);

            Font fontBody = FontFactory.getFont("Montserrat-Light", BaseFont.IDENTITY_H, true, 16, Font.NORMAL, new BaseColor(50, 50, 50));
            String bodyText = "LOREM IPSUM DOLOR SIT AMET, CONSECTETUR\n\nLorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.";
            Paragraph body = new Paragraph(bodyText, fontBody);
            body.setSpacingBefore(20);
            body.setIndentationLeft(30);
            body.setIndentationRight(document.getPageSize().getWidth() * 0.3f);
            document.add(body);

            canvas.setLineWidth(1);
            canvas.setLineDash(new float[]{3, 3}, 0);
            canvas.moveTo(40, 120);
            canvas.lineTo(240, 120);
            canvas.stroke();
            canvas.moveTo(440, 120);
            canvas.lineTo(640, 120);
            canvas.stroke();
            
            Font fontSignature = FontFactory.getFont("Montserrat-Regular", BaseFont.IDENTITY_H, true, 12, Font.NORMAL, new BaseColor(50, 50, 50));
            Paragraph date = new Paragraph("DATE", fontSignature);
            date.setIndentationLeft(100);
            date.setSpacingBefore(20);
            document.add(date);
            
            Paragraph signature = new Paragraph("SIGNATURE", fontSignature);
            signature.setIndentationLeft(510);
            document.add(signature);
            
            
            
            document.close();

            return fileName;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating certificate", e);
        }
    }
}
