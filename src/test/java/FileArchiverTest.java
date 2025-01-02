
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class FileArchiverTest {

    private static final String zipFilePath = "src/test/resources/test.zip";

    @BeforeAll
    static void createZipWithFiles() throws Exception {

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            addToZip(zos, "src/test/resources/IntelliJIDEA_ReferenceCard.pdf");
            addToZip(zos, "src/test/resources/james_bond.xlsx");
            addToZip(zos, "src/test/resources/james_bond_csv.csv");
        }
    }


    @Test
    void checkPdfFileFromZipFileTest() throws Exception {
        try (InputStream is = new FileInputStream(zipFilePath);
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(".pdf")) {
                    PDF pdf = new PDF(zis);
                    Assertions.assertEquals(pdf.numberOfPages, 1);
                }
            }
        }
    }


    @Test
    void checkXlsxFileFromZipFileTest() throws Exception {
        try (InputStream is = new FileInputStream(zipFilePath);
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(".xls")) {
                    XLS xls = new XLS(zis);
                    assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(0).getStringCellValue()).isEqualTo("From Russia with Love");
                    assertThat(xls.excel.getSheetAt(0).getRow(2).getCell(0).getStringCellValue()).isEqualTo("Goldfinger");

                }
            }
        }
    }

    @Test
    void checkCsvFileFromZipFileTest() throws Exception {
        try (InputStream is = new FileInputStream(zipFilePath);
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(".csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> strings = csvReader.readAll();
                    Assertions.assertEquals(7, strings.size());
                }
            }
        }
    }


    @AfterAll
    static void deleteZipFile() throws IOException {
        Files.deleteIfExists(new File(zipFilePath).toPath());
    }


    private static void addToZip(ZipOutputStream zos, String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            ZipEntry zipEntry = new ZipEntry(filePath.substring(filePath.lastIndexOf('/') + 1));
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            zos.closeEntry();
        }
    }


}