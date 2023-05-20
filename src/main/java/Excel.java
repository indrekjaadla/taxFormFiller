import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class Excel {
    public static final String pathName2021 =
            "C:\\Users\\indre\\IdeaProjects\\taxFormFiller\\src\\main\\resources\\U5646616_2021_2021.xlsx";

    public static final String pathName2022 =
            "C:\\Users\\indre\\IdeaProjects\\taxFormFiller\\src\\main\\resources\\U5646616_2022_2022.xlsx";

    private static final String sheetName = "output";

    public static XSSFSheet getExcelSheet(String pathName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(pathName);
        XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
        return workBook.getSheet(sheetName);
    }
}