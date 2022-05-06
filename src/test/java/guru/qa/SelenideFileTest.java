package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.pdftest.assertj.PdfAssert;
import com.codeborne.pdftest.matchers.ContainsExactText;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.hamcrest.MatcherAssert.assertThat;

public class SelenideFileTest {
    @Test
    void zipFileTest () throws Exception {
        ZipFile zipFile = new ZipFile(new File("src/test/resources/zip/Documents.zip"));
        try (ZipInputStream zipInputStream = new ZipInputStream(SelenideFileTest.class.getClassLoader().getResourceAsStream("zip/Documents.zip"))) {

            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                if (fileName.contains(".pdf")) {
                    try (InputStream fileInputStream = zipFile.getInputStream(zipEntry)) {
                        PDF pdfFile = new PDF(fileInputStream);
                        PdfAssert pdfAssert = new PdfAssert(pdfFile);
                        assertThat(pdfFile, new ContainsExactText("Турникет"));
                    }
                }

                if (fileName.contains(".xls")) {
                    try (InputStream fileInputStream = zipFile.getInputStream(zipEntry)) {

                    }
                }

                if (fileName.contains(".csv")) {
                    try (InputStream fileInputStream = zipFile.getInputStream(zipEntry)) {

                    }
                }
            }
        }
    }
}
