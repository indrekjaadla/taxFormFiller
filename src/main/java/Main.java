import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class Main {

    public static WebDriver webDriver;

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
        pause(250);
        for (Entry entry : entries) {

            insertId(entry);
            pause(250);

            insertDescription(entry);
            pause(250);

            insertCountry(entry);
            pause(250);

            insertAssetCategory();
            pause(250);

            insertClosingDate(entry);
            pause(250);

            insertClosingQuantity(entry);
            pause(250);

            insertBuyingCost(entry);
            pause(250);

            insertSellingFee(entry);
            pause(250);

            insertSellingCost(entry);
            pause(250);

            insertPaidIncomeTax(entry);
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

    private static void insertId(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_isinCode\"]")).sendKeys(entry.id);
    }

    private static void insertDescription(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_name\"]")).sendKeys(entry.description);
    }

    private static void insertCountry(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_state\"]")).click();
        webDriver.findElement(By.xpath("//*[@id=\"select-user-input-element\"]")).sendKeys(entry.country);
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_state-1\"]/span")).click();
    }

    private static void insertAssetCategory() {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_type\"]")).click();
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_type-0\"]/span")).click();
    }

    private static void insertClosingDate(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_date\"]")).sendKeys(entry.closingDate);
    }

    private static void insertClosingQuantity(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add_stockfunds_amount\"]")).sendKeys(entry.closingQuantity);
    }

    private static void insertBuyingCost(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-cost-amount-calculator-link\"]")).click();
        fillAndCloseCurrencyCalculator(entry.buyingCost, entry.openingDate, entry.currency);
    }

    private static void insertSellingCost(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-selling-price-calculator-link\"]")).click();
        fillAndCloseCurrencyCalculator(entry.sellingCost, entry.closingDate, entry.currency);
    }

    private static void insertSellingFee(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-appropriation-cost-calculator-link\"]")).click();
        fillAndCloseCurrencyCalculator(entry.sellingFee, entry.closingDate, entry.currency);
    }

    private static void insertPaidIncomeTax(Entry entry) {
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-income-tax-calculator-link\"]")).click();
        fillAndCloseCurrencyCalculator(entry.incomeTax, entry.closingDate, entry.currency);
    }

    private static void fillAndCloseCurrencyCalculator(String amount, String date, String currency) {
        webDriver.findElement(By.xpath("//*[@id=\"currencySum\"]")).sendKeys(amount);
        webDriver.findElement(By.xpath("//*[@id=\"currency\"]")).click();
        webDriver.findElement(By.xpath("//*[@id=\"select-user-input-element\"]")).sendKeys(currency);
        webDriver.findElement(By.xpath("//*[@id=\"currency-0\"]/span")).click();
        webDriver.findElement(By.xpath("//*[@id=\"currencyDate\"]")).sendKeys(date);
        webDriver.findElement(By.xpath("//*[@id=\"currency-calculator-calculate-button\"]")).click();
    }

    private static void saveTransactionRow() {
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-save-button\"]")).click();
    }

    private static void cancelTransactionRow() {
        webDriver.findElement(By.xpath("//*[@id=\"add-stockfunds-cancel-button\"]")).click();
    }
}