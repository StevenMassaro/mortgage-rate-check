package com.massaro.mortgagerates;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RateChecker {

    private final TelegramAPI telegramAPI;

    public RateChecker(TelegramAPI telegramAPI) {
        this.telegramAPI = telegramAPI;
    }

    /**
     * Check the mortgage rates and send a Telegram message with the results.
     */
    public void checkRates() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox");
        WebDriver driver = new ChromeDriver(options);
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().setSize(new Dimension(234, 556));

            System.out.println("Loading rate page and autofilling...");
            driver.get("https://widgets.mortgagenewsdaily.com/widget/f/rates?t=expanded&sn=true&sc=true&c=336699&u=&cbu=&w=218&h=430");

            TimeUnit.SECONDS.sleep(10);
            System.out.println("Taking screenshot...");
            byte[] results = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            System.out.println("Sending Telegram message...");
            telegramAPI.sendPhoto(results);
            System.out.println("Completed rate check on " + new Date());
        } finally {
            driver.quit();
        }
    }
}
