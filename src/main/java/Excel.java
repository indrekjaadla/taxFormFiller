import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
    public static void readCells(String pathName) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File(pathName));
        XSSFWorkbook wb = new XSSFWorkbook(fis);
    }
}
