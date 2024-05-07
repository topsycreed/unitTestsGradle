package utils;

import io.restassured.response.Response;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PdfUtils {
    public void savePdf(Response response, String fileName) {
        if (response != null) {
            // Получение содержимого ответа в виде InputStream
            try (InputStream inputStream = response.getBody().asInputStream()) {
                // Создание файла для сохранения PDF
                OutputStream outputStream = new FileOutputStream(fileName);

                // Копирование содержимого InputStream в файл
                int bytesRead;
                byte[] buffer = new byte[4096];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                // Закрытие потоков
                inputStream.close();
                outputStream.close();

                System.out.println("PDF успешно сохранен в файл: " + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String readPdf(String fileName) {
        String text = null;
        try {
            // Указать путь к вашему PDF-файлу
            File file = new File(fileName);

            // Загрузка PDF-документа
            PDDocument document = PDDocument.load(file);

            // Создание объекта PDFTextStripper
            PDFTextStripper pdfStripper = new PDFTextStripper();

            // Получение текста из PDF
            text = pdfStripper.getText(document);

            // Закрытие документа
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public void printPdf(String fileName) {
        try {
            // Указать путь к вашему PDF-файлу
            File file = new File(fileName);

            // Загрузка PDF-документа
            PDDocument document = PDDocument.load(file);

            // Создание объекта PDFTextStripper
            PDFTextStripper pdfStripper = new PDFTextStripper();

            // Получение текста из PDF
            String text = pdfStripper.getText(document);
            System.out.println("Содержимое PDF:\n" + text);

            // Закрытие документа
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
