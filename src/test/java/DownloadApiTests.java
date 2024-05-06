import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@Story("Download API")
class DownloadApiTests {

    @Test
    void uploadTest() {
        int petId = 1;
        String url = String.format("https://petstore.swagger.io/v2/pet/%s/uploadImage", petId);
        String fileName = "Octocat.png";
        File file = new File(fileName);

        given().
                when().
                    multiPart("file", file, "image/png").
                    accept("application/json").
                    post(url).
                then().
                    log().
                    all().
                    assertThat().
                    contentType(ContentType.JSON).
                    statusCode(200).
                    body("code", equalTo(200)).
                    body("type", equalTo("unknown")).
                    body("message", containsString("File uploaded to ./" + fileName));
    }

    @Test
    void testDownloadHttpClient() throws IOException {
        String endpoint = "https://alfabank.servicecdn.ru/site-upload/67/dd/356/zayavlenie-IZK.pdf";
        String fileName = "downloaded.pdf";

        Response response =
                given().
                        when().
                            get(endpoint).
                        then().
                            contentType("application/pdf").
                            statusCode(200).
                        extract().response();
        savePdf(response, fileName);
        String pdfText = readPdf(fileName);
        assertThat(pdfText).contains("Чтобы стать индивидуальным зарплатным клиентом,")
                .contains("нужно получать на карту зарплату от 30 000 ₽ в месяц");
    }

    private void savePdf(Response response, String fileName) {
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

    private String readPdf(String fileName) {
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

    private void printPdf(String fileName) {
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
