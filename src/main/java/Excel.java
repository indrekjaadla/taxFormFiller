import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Excel {
    public static void readCells(String pathName) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File(pathName));
    }
}
