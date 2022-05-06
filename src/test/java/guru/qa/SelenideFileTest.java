package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.pdftest.assertj.PdfAssert;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SelenideFileTest {

    ClassLoader classLoader = SelenideFileTest.class.getClassLoader();

    @DisplayName("Наёдём и проверим содержимое PDF файла")
    @Test
    void zipPDFFileTest () throws Exception {
        ZipFile zipFile = new ZipFile(new File("src/test/resources/zip/Documents.zip"));
        try (ZipInputStream zipInputStream = new ZipInputStream(classLoader.getResourceAsStream("zip/Documents.zip"))) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                if (fileName.contains(".pdf")) {
                    try (InputStream fileInputStream = zipFile.getInputStream(zipEntry)) {
                        PDF pdfFile = new PDF(fileInputStream);
                        PdfAssert pdfAssert = new PdfAssert(pdfFile);
                        pdfAssert.containsText("Турникет");
                    }
                }
            }
        }
    }
    @DisplayName("Наёдём и проверим содержимое XLS файла")
    @Test
    void zipXLSFileTest () throws Exception {
        ZipFile zipFile = new ZipFile(new File("src/test/resources/zip/Documents.zip"));
        try (ZipInputStream zipInputStream = new ZipInputStream(classLoader.getResourceAsStream("zip/Documents.zip"))) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                if (fileName.contains(".xls")) {
                    try (InputStream fileInputStream = zipFile.getInputStream(zipEntry)) {
                        XLS xlsFile = new XLS(fileInputStream);
                        String anyCell = xlsFile.excel.getSheetAt(1).getRow(17).getCell(0).getStringCellValue();
                        System.out.println(anyCell);
                        assertThat(anyCell).contains("Видеосервер");
                    }
                }
            }
        }
    }
    @DisplayName("Наёдём и проверим содержимое CSV файла")
    @Test
    void zipCSVFileTest () throws Exception {
        ZipFile zipFile = new ZipFile(new File("src/test/resources/zip/Documents.zip"));
        try (ZipInputStream zipInputStream = new ZipInputStream(classLoader.getResourceAsStream("zip/Documents.zip"))) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                if (fileName.contains(".csv")) {
                    try (InputStream fileInputStream = zipFile.getInputStream(zipEntry)) {
                        CSVReader csvReader = new CSVReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
                        List<String[]> csvContent = csvReader.readAll();
                        assertThat(csvContent).contains(new String[] {"Username", " Identifier", "First name", "Last name"});
                    }
                }
            }
        }
    }
}
