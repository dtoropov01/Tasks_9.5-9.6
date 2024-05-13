import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class smpTest {
    static WebDriver driver;

    @BeforeAll
    public static void setup()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1400, 1200));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    public void addObject()
    {
        login();

        // открыли избранное
        waitElement("//div[@id='gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9']/div");
        click("//div[@id='gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9']/div");

        // нажать кнопку добавить в избранное
        click("//span[@id='favorite']");
        waitElement("//input[@id='gwt-debug-itemTitle-value']");

        // заполнить название
        click("//input[@id='gwt-debug-itemTitle-value']");
        driver.findElement(By.xpath("//input[@id='gwt-debug-itemTitle-value']")).clear();
        driver.findElement(By.xpath("//input[@id='gwt-debug-itemTitle-value']")).sendKeys("Торопов Дмитрий");
        waitElement("//div[@id='gwt-debug-apply']");

        // нажать сохранить
        click("//div[@id='gwt-debug-apply']");

        // проверить
        WebElement element = driver.findElement(By.xpath("//a[@id='gwt-debug-title']/div"));
        String textElement = element.getText();
        String msg = String.format("Название объекта не совпало. Ожидалось: %s, Получили: %s", "Торопов Дмитрий", textElement);
        Assertions.assertEquals("Торопов Дмитрий", textElement, msg);

        // очистка
        click("//span[@id='gwt-debug-editFavorites']");
        waitElement("//table[@id='gwt-debug-favoritesEditTable']/tbody/tr/td[6]/div/span");
        click("//table[@id='gwt-debug-favoritesEditTable']/tbody/tr/td[6]/div/span");
        waitElement("//div[@id='gwt-debug-YES']");
        click("//div[@id='gwt-debug-YES']");
        waitElement("//div[@id='gwt-debug-apply']");
        click("//div[@id='gwt-debug-apply']");

        // закрыли избранное
        waitElement("//div[@id='gwt-debug-collapseNavTreeButton']/div");
        click("//div[@id='gwt-debug-collapseNavTreeButton']/div");
    }

    @Test
    public void deleteObject()
    {
        login();

        // открыли избранное
        waitElement("//div[@id='gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9']/div");
        click("//div[@id='gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9']/div");

        // нажать кнопку добавить в избранное
        click("//span[@id='favorite']");
        waitElement("//input[@id='gwt-debug-itemTitle-value']");

        // заполнить название
        click("//input[@id='gwt-debug-itemTitle-value']");
        driver.findElement(By.xpath("//input[@id='gwt-debug-itemTitle-value']")).clear();
        driver.findElement(By.xpath("//input[@id='gwt-debug-itemTitle-value']")).sendKeys("Торопов Дмитрий");
        waitElement("//div[@id='gwt-debug-apply']");

        // нажать сохранить
        click("//div[@id='gwt-debug-apply']");

        // удалить
        click("//span[@id='gwt-debug-editFavorites']");
        waitElement("//table[@id='gwt-debug-favoritesEditTable']/tbody/tr/td[6]/div/span");
        click("//table[@id='gwt-debug-favoritesEditTable']/tbody/tr/td[6]/div/span");
        waitElement("//div[@id='gwt-debug-YES']");
        click("//div[@id='gwt-debug-YES']");
        waitElement("//div[@id='gwt-debug-apply']");
        click("//div[@id='gwt-debug-apply']");

        // проверить
        WebElement element = driver.findElement(By.xpath("//a[@id='gwt-debug-title']/div"));
        Assertions.assertTrue(element.isDisplayed(), "Объект не удалился");

        // закрыли избранное
        waitElement("//div[@id='gwt-debug-collapseNavTreeButton']/div");
        click("//div[@id='gwt-debug-collapseNavTreeButton']/div");
    }

    private void login()
    {
        driver.get("https://test-m.sd.nau.run/sd/");

        // залогиниться
        driver.findElement(By.id("username")).sendKeys("d_toropov01");
        driver.findElement(By.id("password")).sendKeys("123");
        click("//input[@id='submit-button']");
        waitElement("//span[@id='favorite']");
    }

    public WebElement waitElement(String xpath)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        return element;
    }

    public void click(String xpath)
    {
        waitElement(xpath).click();
    }

    @AfterEach
    public void logout()
    {
        click("//a[@id='gwt-debug-logout']");
    }

    @AfterAll
    public static void close()
    {
        driver.close();
    }
}
