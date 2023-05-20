import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class Main {

    public static WebDriver webDriver;
    public static final int waitAfterAdd = 1000;
    public static final int waitAfterClick = 200;
    public static final int waitAfterSend = 500;
    public static final int waitBeforeSave = 500;

    public static void main(String[] args) {

        //SETUP Command Prompt ->
        // chrome.exe --remote-debugging-port=9999 --user-data-dir="C:\Users\indre\IdeaProjects\taxFormFiller\chromeData"
        //SETUP navigate to emta.ee -> login -> tuludeklaratsioon

        setSystemProperties();
        int portNumber = 9999;
        ChromeOptions chromeOptions = setChromeOptions(portNumber);
        webDriver = new ChromeDriver(chromeOptions);

        List<Entry> entries = Entry.getEntriesFromExcelSheet(Excel.pathName2021);
        addTransactionRow();
        for (Entry entry : entries) {
            insertId(entry);
            insertDescription(entry);
            insertCountry(entry);
            insertAssetCategory();
            insertClosingDate(entry);
            insertClosingQuantity(entry);
            insertBuyingCost(entry);
            insertSellingFee(entry);
            insertSellingCost(entry);
            insertPaidIncomeTax(entry);
            saveTransactionRow();
            pause(1000);
        }
        cancelTransactionRow();
        pause(1000);
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

    private static void pause(int timeInMillis) {
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    private static void addTransactionRow() {
        webDriver.findElement(By.xpath("//*[@id=\"stockfunds-new-row-button\"]")).click();
        pause(waitAfterAdd);
    }

    private static void insertId(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_isinCode\"]")).sendKeys(entry.id);
        pause(waitAfterSend);
    }

    private static void insertDescription(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_name\"]")).sendKeys(entry.description);
        pause(waitAfterSend);
    }

    private static void insertCountry(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_state\"]")).click();
        pause(waitAfterClick);
        webDriver.findElement(By.xpath("//*[@id=\"select-user-input-element\"]")).sendKeys(entry.country);
        pause(waitAfterSend);
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_state-1\"]/span")).click();
        pause(waitAfterClick);
    }

    private static void insertAssetCategory() {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_type\"]")).click();
        pause(waitAfterClick);
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_type-0\"]/span")).click();
        pause(waitAfterClick);
    }

    private static void insertClosingDate(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_date\"]")).sendKeys(entry.closingDate);
        pause(waitAfterSend);
    }

    private static void insertClosingQuantity(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_amount\"]")).sendKeys(entry.closingQuantity);
        pause(waitAfterSend);
    }

    private static void insertBuyingCost(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-cost-amount-calculator-link\"]")).click();
        pause(waitAfterClick);
        fillAndCloseCurrencyCalculator(entry.buyingCost, entry.openingDate, entry.currency);
    }

    private static void insertSellingCost(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-selling-price-calculator-link\"]")).click();
        pause(waitAfterClick);
        fillAndCloseCurrencyCalculator(entry.sellingCost, entry.closingDate, entry.currency);
    }

    private static void insertSellingFee(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-appropriation-cost-calculator-link\"]")).click();
        pause(waitAfterClick);
        fillAndCloseCurrencyCalculator(entry.sellingFee, entry.closingDate, entry.currency);
    }

    private static void insertPaidIncomeTax(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-income-tax-calculator-link\"]")).click();
        pause(waitAfterClick);
        fillAndCloseCurrencyCalculator(entry.incomeTax, entry.closingDate, entry.currency);
    }

    private static void fillAndCloseCurrencyCalculator(String amount, String date, String currency) {
        webDriver.findElement(By.xpath("//*[@id=\"currencySum\"]")).sendKeys(amount);
        pause(waitAfterSend);
        webDriver.findElement(By.xpath("//*[@id=\"currency\"]")).click();
        pause(waitAfterClick);
        webDriver.findElement(By.xpath("//*[@id=\"select-user-input-element\"]")).sendKeys(currency);
        pause(waitAfterSend);
        webDriver.findElement(By.xpath("//*[@id=\"currency-0\"]/span")).click();
        pause(waitAfterClick);
        webDriver.findElement(By.xpath("//*[@id=\"currencyDate\"]")).sendKeys(date);
        pause(waitAfterSend);
        webDriver.findElement(By.xpath("//*[@id=\"currency-calculator-calculate-button\"]")).click();
        pause(waitAfterClick);
    }

    private static void saveTransactionRow() {
        pause(waitBeforeSave);
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-save-button\"]")).click();
        pause(waitAfterClick);
    }

    private static void cancelTransactionRow() {
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-cancel-button\"]")).click();
        pause(waitAfterClick);
    }
}