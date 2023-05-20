import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Excel {
    private static final String pathName2021 =
            "C:\\Users\\indre\\IdeaProjects\\taxFormFiller\\src\\main\\resources\\U5646616_2021_2021.xlsx";

    private static final String pathName2022 =
            "C:\\Users\\indre\\IdeaProjects\\taxFormFiller\\src\\main\\resources\\U5646616_2022_2022.xlsx";


    public static void test() {
        readCells(pathName2021);
    }

    public static List<Entry> readCells(String pathName) {
        String sheetName = "output";

        String datePattern = "dd.MM.yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);

        try {
            FileInputStream fileInputStream = new FileInputStream(pathName);
            XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workBook.getSheet(sheetName);

            List<Entry> entries = new ArrayList<>();
            for (Row row : sheet) {
                Entry entry = new Entry();


                entry.id = row.getCell(0).getStringCellValue();
                entry.description = row.getCell(1).getStringCellValue();

                entry.openingDate = simpleDateFormat.format(row.getCell(2).getDateCellValue());
                entry.closingDate = simpleDateFormat.format(row.getCell(3).getDateCellValue());

                entry.closingQuantity = (int) row.getCell(4).getNumericCellValue();
                entry.buyingCost = row.getCell(5).getNumericCellValue();
                entry.sellingFee = row.getCell(6).getNumericCellValue();
                entry.sellingCost = row.getCell(7).getNumericCellValue();
                entry.withholdingTax = row.getCell(8).getNumericCellValue();
                entry.country = row.getCell(9).getStringCellValue();
                entry.currency = row.getCell(10).getStringCellValue();
                entries.add(entry);
            }

            System.out.println(entries.get(0).openingDate);
            System.out.println(entries.get(0).closingDate);
            return entries;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}