package mortgage;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RateCheck {

    private static final String PURCHASE_PRICE = System.getenv("PURCHASE_PRICE");
    private static final String DOWN_PAYMENT = System.getenv("DOWN_PAYMENT");
    private static final String ZIP_CODE = System.getenv("ZIP_CODE");
    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private static final String CHAT_ID = System.getenv("CHAT_ID");

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting rate check on " + new Date());
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().setSize(new Dimension(1080, 1080));

        System.out.println("Loading rate page and autofilling...");
        driver.get("https://member-mortgage.cuofco.org/#/rate-selection");
        WebElement purchasePriceInputBox = findInputByName(driver, "purchasePrice");
        purchasePriceInputBox.clear();
        purchasePriceInputBox.sendKeys(PURCHASE_PRICE);
        WebElement downPaymentDollars = driver.findElement(By.xpath("/html/body/platform-shell/div/public-layout/micro-app/div/pricing-index/div/consumer-pricing-container/div/mortgage-quote/div[1]/div[2]/div[3]/ui-range-slider/div[2]/ui-input/div/input"));
        downPaymentDollars.clear();
        downPaymentDollars.sendKeys(DOWN_PAYMENT);
        WebElement zip = findInputByName(driver, "RateSelectionZipCode");
        zip.sendKeys(ZIP_CODE);
        WebElement creditScoreDropdown = driver.findElement(By.xpath("/html/body/platform-shell/div/public-layout/micro-app/div/pricing-index/div/consumer-pricing-container/div/mortgage-quote/div[1]/div[2]/div[5]/div[2]/ui-container/div/ui-options/div[1]/div/input"));
        creditScoreDropdown.click();
        WebElement creditScore780 = driver.findElement(By.xpath("/html/body/platform-shell/div/public-layout/micro-app/div/pricing-index/div/consumer-pricing-container/div/mortgage-quote/div[1]/div[2]/div[5]/div[2]/ui-container/div/ui-options/div[1]/div/ul/li[2]"));
        creditScore780.click();
        WebElement propertyTypeDropdown = driver.findElement(By.xpath("/html/body/platform-shell/div/public-layout/micro-app/div/pricing-index/div/consumer-pricing-container/div/mortgage-quote/div[1]/div[2]/div[6]/div[2]/ui-container/div/ui-options/div[1]/div/input"));
        propertyTypeDropdown.click();
        WebElement singleFamilyHome = driver.findElement(By.xpath("/html/body/platform-shell/div/public-layout/micro-app/div/pricing-index/div/consumer-pricing-container/div/mortgage-quote/div[1]/div[2]/div[6]/div[2]/ui-container/div/ui-options/div[1]/div/ul/li[2]"));
        singleFamilyHome.click();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Loading rates...");
        WebElement seeMyRatesButton = driver.findElement(By.id("seeMyRatesButton"));
        seeMyRatesButton.click();
        TimeUnit.SECONDS.sleep(15);
        System.out.println("Taking screenshot...");
        byte[] results = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        driver.quit();

        System.out.println("Sending Telegram message...");
        // send telegram message with details
        TelegramBot telegramBot = new TelegramBot(BOT_TOKEN);
        SendPhoto sendPhoto = new SendPhoto(CHAT_ID, results);
        SendResponse sendResponse = telegramBot.execute(sendPhoto);
        boolean ok = sendResponse.isOk();
        System.out.println("Completed rate check on " + new Date());
    }

    private static WebElement findInputByName(WebDriver driver, String name) {
        List<WebElement> elements = driver.findElements(By.id("input"));
        for (WebElement element : elements) {
            if (element.getDomAttribute("name").equals(name)) {
                return element;
            }
        }
        return null;
    }
}