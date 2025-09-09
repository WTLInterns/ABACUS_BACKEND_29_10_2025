package com.example.Abacus.Service;

import com.example.Abacus.Model.Payment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ReceiptService {

    public String generateReceipt(Payment payment) {
        try {
            String fileName = "receipt_" + payment.getReceiptNo() + ".pdf";

            // Small receipt size
            Rectangle pageSize = new Rectangle(226, 600);
            Document document = new Document(pageSize, 20, 20, 20, 20);
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // ===== HEADER =====
            Paragraph header = new Paragraph("ABACUS INSTITUTE",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK));
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            Paragraph subHeader = new Paragraph("PAYMENT RECEIPT",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            subHeader.setAlignment(Element.ALIGN_CENTER);
            subHeader.setSpacingAfter(10f);
            document.add(subHeader);

            addSeparator(document);

            // ===== INFO =====
            document.add(new Paragraph("Receipt No: " + payment.getReceiptNo()));
            document.add(new Paragraph("Date: " + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));
            document.add(new Paragraph("Teacher: " + payment.getTeacher().getFirstName()
                    + " " + payment.getTeacher().getLastName()));
            document.add(new Paragraph("Email: " + payment.getTeacher().getEmail()));

            addSeparator(document);

            // ===== PAYMENT DETAILS =====
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.addCell(getCell("Total Fees", true));
            table.addCell(getCell(String.valueOf(payment.getFees()), false));
            table.addCell(getCell("Paid", true));
            table.addCell(getCell(String.valueOf(payment.getPaid()), false));
            table.addCell(getCell("Remaining", true));
            table.addCell(getCell(String.valueOf(payment.getRemainingAmount()), false));
            table.addCell(getCell("Payment Mode", true));
            table.addCell(getCell(payment.getPaymentMode(), false));
            document.add(table);

            addSeparator(document);

            // ===== FOOTER =====
            Paragraph footer = new Paragraph("Thank you for your payment!",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, BaseColor.DARK_GRAY));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20f);
            document.add(footer);

            // ===== QR CODE =====
            String qrContent = "http://10.183.165.233:8085/payments/" + payment.getReceiptNo();
            Image qrImage = generateQRCode(qrContent, 120, 120);
            qrImage.setAlignment(Element.ALIGN_CENTER);
            document.add(qrImage);

            document.close();
            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Error while generating receipt PDF", e);
        }
    }

    private void addSeparator(Document document) throws DocumentException {
        Paragraph separator = new Paragraph("----------------------------------------",
                FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.GRAY));
        separator.setAlignment(Element.ALIGN_CENTER);
        separator.setSpacingBefore(5f);
        separator.setSpacingAfter(5f);
        document.add(separator);
    }

    private PdfPCell getCell(String content, boolean bold) {
        Font font = bold
                ? FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)
                : FontFactory.getFont(FontFactory.HELVETICA, 10);
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        return cell;
    }

    // 🔹 Generate QR code image using ZXing
    private Image generateQRCode(String text, int width, int height) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        return Image.getInstance(baos.toByteArray());
    }
}
