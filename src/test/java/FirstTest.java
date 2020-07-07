import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.google.common.io.Files;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class FirstTest {
    @BeforeClass
    public static void setUp() {
        Configuration.browser = "firefox";
        Configuration.baseUrl = "https://yandex.ru";
        SelenideLogger.addListener("allure", new AllureSelenide().screenshots(true).savePageSource(true));
        closeWebDriver();
    }

    @Test
    public void helloWorldTest() {
        open("/");
        $(By.name("text")).setValue("selenide").pressEnter();
        $("#search-result").shouldHave(Condition.visible).shouldHave(text("Selenide.org"));
    }

    @After
    public void tearDown() throws IOException {
        screenshot();
        SelenideLogger.removeListener("allure");
    }

    @Attachment(type = "image/png")
    public byte[] screenshot() throws IOException {
        File screenshots = Screenshots.getLastScreenshot();
        return screenshots == null ? null : Files.toByteArray(screenshots);
    }
}
