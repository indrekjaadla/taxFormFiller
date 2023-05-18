import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {

    public static WebDriver webDriver;

    public static void main(String[] args) throws InterruptedException {
        //SETUP Command Prompt ->
        // chrome.exe --remote-debugging-port=9999 --user-data-dir="C:\Users\indre\IdeaProjects\taxFormFiller\chromeData"
        //SETUP navigate to emta.ee -> login -> tuludeklaratsioon

        setSystemProperties();
        int portNumber = 9999;
        ChromeOptions chromeOptions = setChromeOptions(portNumber);
        webDriver = new ChromeDriver(chromeOptions);



        String id = "US02361E1082";
        String description = "ALLIED HEALTHCARE PRODUCTS";
        String country = "Ameerika";
        String closingDate = "17.09.2021";
        String closingQuantity = "21";


        addTransactionRow();
        pause(250);
        for (int i = 0; i < 3; i++) {

            insertId(id);
            pause(250);

            insertDescription(description);
            pause(250);

            insertCountry(country);
            pause(250);

            insertAssetCategory();
            pause(250);

            insertClosingDate(closingDate);
            pause(250);

            insertClosingQuantity(closingQuantity);
            pause(250);

            insertBuyingCost();
            pause(250);

            insertSellingFee();
            pause(250);

            insertSellingCost();
            pause(250);

            insertPaidIncomeTax();
            pause(250);

            saveTransactionRow();
            pause(1000);
        }
        cancelTransactionRow();
        pause(250);

        webDriver.quit();
    }

    private static void setSystemProperties() {
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Users\\indre\\IdeaProjects\\taxFormFiller\\chromedriver.exe"
        );
        System.setProperty(
                "webdriver.http.factory",
                "jdk-http-client"
        );
    }

    private static ChromeOptions setChromeOptions(int portNumber) {
        ChromeOptions options = new ChromeOptions();
        return options.setExperimentalOption(
                "debuggerAddress",
                "127.0.0.1:" + portNumber
        );
    }

    private static void pause(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    private static void addTransactionRow() {
        webDriver.findElement(By.xpath("//*[@id=\"stockfunds-new-row-button\"]")).click();
    }

    private static void insertId(String id) {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_isinCode\"]")).sendKeys(id);
    }

    private static void insertDescription(String description) {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_name\"]")).sendKeys(description);
    }

    private static void insertCountry(String country) {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_state\"]")).click();
        webDriver.findElement(By.xpath("//*[@id=\"select-user-input-element\"]")).sendKeys(country);
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_state-1\"]/span")).click();
    }

    private static void insertAssetCategory() {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_type\"]")).click();
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_type-0\"]/span")).click();
    }

    private static void insertClosingDate(String closingDate) {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_date\"]")).sendKeys(closingDate);
    }

    private static void insertClosingQuantity(String quantity) {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_amount\"]")).sendKeys(quantity);
    }

    private static void insertBuyingCost() {
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-cost-amount-calculator-link\"]")).click();
        fillAndCloseCurrencyCalculator("215.88", "30.08.2021");
    }

    private static void insertSellingCost() {
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-selling-price-calculator-link\"]")).click();
        fillAndCloseCurrencyCalculator("156,42", "17.09.2021");
    }

    private static void insertSellingFee() {
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-appropriation-cost-calculator-link\"]")).click();
        fillAndCloseCurrencyCalculator("1.0", "17.09.2021");
    }

    private static void insertPaidIncomeTax() {
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-income-tax-calculator-link\"]")).click();
        fillAndCloseCurrencyCalculator("0.0", "17.09.2021");
    }

    private static void fillAndCloseCurrencyCalculator(String proceeds,
                                                       String openDate) {

        webDriver.findElement(By.xpath("//*[@id=\"currencySum\"]")).sendKeys(proceeds);

        webDriver.findElement(By.xpath("//*[@id=\"currency\"]")).click();
        webDriver.findElement(By.xpath("//*[@id=\"currency-0\"]/span")).click();

        webDriver.findElement(By.xpath("//*[@id=\"currencyDate\"]")).sendKeys(openDate);

        webDriver.findElement(By.xpath("//*[@id=\"currency-calculator-calculate-button\"]")).click();
    }

    private static void saveTransactionRow() {
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-save-button\"]")).click();
    }

    private static void cancelTransactionRow() {
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-cancel-button\"]")).click();
    }
}