package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeleniumTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestSuccess() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("form[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("span[class='button__text']")).click();
        String successText = driver.findElement(By.cssSelector("p[class='paragraph paragraph_theme_alfa-on-white']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", successText.trim());
    }

    @Test
    void shouldTestErrorName() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("form[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Vasiliy");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("span[class='button__text']")).click();
        String unsuccessText = driver.findElement(By.cssSelector("span[class='input__sub']")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", unsuccessText.trim());
    }

    @Test
    void shouldTestErrorPhone() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("form[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        form.findElement(By.cssSelector("span[class='button__text']")).click();
        String unsuccessColor = driver.findElement(By.cssSelector("span[class='checkbox__text']")).getCssValue("color");;
        assertEquals("rgba(255, 92, 92, 1)", unsuccessColor.trim());
    }
}
