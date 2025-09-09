package com.example.Abacus.Service;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.example.Abacus.Model.Payment;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ReceiptService {

    public String generateReceipt(Payment payment) {
        try {
            String fileName = "receipt_" + payment.getReceiptNo() + ".pdf";

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // ===== HEADER =====
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLUE);
            Paragraph header = new Paragraph("ABACUS INSTITUTE", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            Font subHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Paragraph subHeader = new Paragraph("Payment Receipt", subHeaderFont);
            subHeader.setAlignment(Element.ALIGN_CENTER);
            subHeader.setSpacingAfter(20f);
            document.add(subHeader);

            // ===== RECEIPT INFO =====
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);

            infoTable.addCell(getCell("Receipt No:", true));
            infoTable.addCell(getCell(payment.getReceiptNo(), false));

            infoTable.addCell(getCell("Date:", true));
            infoTable.addCell(getCell(LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), false));

            infoTable.addCell(getCell("Teacher:", true));
            infoTable.addCell(getCell(payment.getTeacher().getFirstName() + " " + payment.getTeacher().getLastName(), false));

            infoTable.addCell(getCell("Email:", true));
            infoTable.addCell(getCell(payment.getTeacher().getEmail(), false));

            document.add(infoTable);
            document.add(new Paragraph("\n"));

            // ===== PAYMENT DETAILS =====
            Paragraph paymentDetails = new Paragraph("Payment Details", subHeaderFont);
            paymentDetails.setSpacingAfter(10f);
            document.add(paymentDetails);

            PdfPTable paymentTable = new PdfPTable(2);
            paymentTable.setWidthPercentage(100);

            paymentTable.addCell(getHeaderCell("Total Fees"));
            paymentTable.addCell(getValueCell(String.valueOf(payment.getFees())));

            paymentTable.addCell(getHeaderCell("Paid Amount"));
            paymentTable.addCell(getValueCell(String.valueOf(payment.getPaid())));

            paymentTable.addCell(getHeaderCell("Remaining Amount"));
            paymentTable.addCell(getValueCell(String.valueOf(payment.getRemainingAmount())));

            paymentTable.addCell(getHeaderCell("Payment Mode"));
            paymentTable.addCell(getValueCell(payment.getPaymentMode()));

            document.add(paymentTable);

            // ===== FOOTER =====
            Paragraph footer = new Paragraph("\n\nThank you for your payment!",
                    FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.DARK_GRAY));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Error while generating receipt PDF", e);
        }
    }

    private PdfPCell getCell(String content, boolean bold) {
        Font font = bold
                ? FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)
                : FontFactory.getFont(FontFactory.HELVETICA, 12);
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private PdfPCell getHeaderCell(String content) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell getValueCell(String content) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 12);
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
}
