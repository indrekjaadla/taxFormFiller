import org.apache.poi.ss.usermodel.Row;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Entry {
    public String id;
    public String description;
    public String closingDate;
    public String closingQuantity;
    public String openingDate;
    public String buyingCost;
    public String sellingFee;
    public String sellingCost;
    public String incomeTax;
    public String country;
    public String currency;

    public static List<Entry> getEntriesFromExcelSheet(String pathName) {
        try {
            List<Entry> entries = new ArrayList<>();
            for (Row row : Excel.getExcelSheet(pathName)) {
                entries.add(fillEntry(row));
            }
            return entries;
        } catch (Exception exception) {
            exception.printStackTrace();
            return List.of();
        }
    }

    private static Entry fillEntry(Row row) {
        String datePattern = "dd.MM.yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        Entry entry = new Entry();
        entry.id = row.getCell(0).getStringCellValue();
        entry.description = row.getCell(1).getStringCellValue();
        entry.openingDate = simpleDateFormat.format(row.getCell(2).getDateCellValue());
        entry.closingDate = simpleDateFormat.format(row.getCell(3).getDateCellValue());
        entry.closingQuantity = String.valueOf((int) row.getCell(4).getNumericCellValue());
        entry.buyingCost = BigDecimal.valueOf(row.getCell(5).getNumericCellValue()).toString();
        entry.sellingFee = BigDecimal.valueOf(row.getCell(6).getNumericCellValue()).toString();
        entry.sellingCost = BigDecimal.valueOf(row.getCell(7).getNumericCellValue()).toString();
        entry.incomeTax = BigDecimal.valueOf(row.getCell(8).getNumericCellValue()).toString();
        entry.country = row.getCell(9).getStringCellValue();
        entry.currency = row.getCell(10).getStringCellValue();
        return entry;
    }
}