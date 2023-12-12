package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class Task extends TimerTask implements Runnable{
   private final Config config;

    public Task(Config config) {
        this.config = config;
    }

    @Override
    public void run() {
        // Создание объекта ChromeOptions
        ChromeOptions chromeOptions = new ChromeOptions();

        // Добавление аргумента для игнорирования ошибок сертификатов
        chromeOptions.addArguments("--ignore-certificate-errors");

        // Добавление аргумента для игнорирования ошибок SSL
        chromeOptions.addArguments("--ignore-ssl-errors");

        // Добавление аргумента для использования фейкового пользовательского интерфейса для медиапотока
        chromeOptions.addArguments("--use-fake-ui-for-media-stream");

        chromeOptions.setExperimentalOption("prefs", Map.of(
                "profile.default_content_setting_values.media_stream_mic", 1,
                "profile.default_content_setting_values.media_stream_camera", 1,
                "profile.default_content_setting_values.geolocation", 1));

        // Добавление аргумента для запуска браузера без использования песочницы (sandbox)
        chromeOptions.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(chromeOptions);

        try {
            driver.manage().window().maximize();

            driver.get("https://teams.microsoft.com");

            Duration duration = Duration.ofSeconds(20 * 60);
            WebDriverWait wait = new WebDriverWait(driver, duration);

            WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("i0116")));
            emailInput.sendKeys(config.getEmail());

            WebElement button1 = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
            button1.click();

            WebElement passwordInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("i0118")));
            passwordInput.sendKeys(config.getPassword());

            WebElement button2 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input#idSIButton9.button_primary")));
            button2.click();

            WebElement button3 = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
            button3.click();

            WebElement teams = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#app-bar-2a84919f-59d8-4441-a975-2a8c2643b741")));
            teams.click();

            WebElement close = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#toast-container > div > div > div.toast-message > div > button:nth-child(2)")));
            close.click();

            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='" + config.getRoomName() + "']")));
            element.click();

            int maxAttempts = 5;
            int attempt = 0;

            while (attempt < maxAttempts) {

                driver.navigate().refresh();

                try {
                    WebElement button = new WebDriverWait(driver, Duration.ofSeconds(3*60))
                            .until(ExpectedConditions.presenceOfElementLocated(By.id("[aria-label='" + config.getRoomName() + "']")));
                    button.click();

                    break;

                } catch (Exception e) {
                    attempt++;
                }
            }
            WebElement button5 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.ts-calling-join-button")));
            button5.click();

            try {
                Thread.sleep(Duration.ofSeconds(10));
            }catch (Exception e){
                e.printStackTrace();
            }
            // Использование Actions для последовательного нажатия клавиши Tab
            Actions actions = new Actions(driver);

            for (int i = 0; i < 12; i++) {
                actions.sendKeys(Keys.TAB);
            }

            // Нажатие клавиши Enter
            actions.sendKeys(Keys.ENTER);

            Timer timerForClose = new Timer();
            Duration durationOfLesson = Duration.ofMinutes(config.getDuration());
            timerForClose.schedule(new TimerTask() {
                @Override
                public void run() {
                    driver.quit();
                    timerForClose.cancel();  // Отменяем таймер после выполнения задачи
                }
            }, durationOfLesson.toMillis());

        } catch (Exception e) {
            driver.quit();
            e.printStackTrace();
        }finally {
            driver.quit();
        }
    }
}
