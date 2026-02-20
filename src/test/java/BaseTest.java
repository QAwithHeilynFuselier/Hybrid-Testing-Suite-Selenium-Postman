import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.net.URL;
import java.net.MalformedURLException;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Duration;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.ClientConfig;
import java.net.URL;
import java.time.Duration;

public class BaseTest {

    protected String url;
  protected WebDriver driver;
    protected WebDriverWait wait;
   protected Actions actions;
    @BeforeMethod
    @Parameters({"baseUrl", "browser", "gridUrl"})
    public void setUp(
            @Optional("https://qa.koel.app/") String baseUrl,
            @Optional("chrome") String browser,
            @Optional("") String gridUrl) {

        this.url = baseUrl; // Guardamos la URL en la variable de la clase
        launchBrowser(this.url, browser, gridUrl);
    }

    public void launchBrowser(String baseUrl, String browser, String gridUrl) {
        // 1. Prioridad a variables de sistema (YAML), si no, usa TestNG
        String finalGridUrl = System.getProperty("gridUrl", gridUrl);
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        // Esto quita los errores de "Unable to find CDP implementation" en consola
        chromeOptions.setCapability("browserVersion", "131");

        if (headless || (finalGridUrl != null && !finalGridUrl.isEmpty())) {
            chromeOptions.addArguments("--headless=new");
            chromeOptions.addArguments("--disable-gpu");
        }

        try {
            if (finalGridUrl != null && !finalGridUrl.isEmpty()) {
                System.out.println("🚀 GRID MODE: " + finalGridUrl);
                ClientConfig config = ClientConfig.defaultConfig()
                        .connectionTimeout(Duration.ofMinutes(3))
                        .readTimeout(Duration.ofMinutes(3));

                driver = RemoteWebDriver.builder()
                        .address(new URL(finalGridUrl))
                        .oneOf(chromeOptions)
                        .config(config)
                        .build();
            } else {
                System.out.println("💻 LOCAL MODE");
                // Usamos WebDriverManager para evitar que Chrome v145 rompa el test
                io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions);
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            this.actions = new Actions(driver);

            driver.get(baseUrl);

        } catch (Exception e) {
            System.err.println("❌ Fallo en el Driver: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }







    public void navigatetoPage() {
        driver.get(url);
    }

//usefull for all my test
       public void takeScreenshot(String name) {
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File destFile = new File("./screenshots/" + name + ".png");
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Attachment(value = "Screenshot on Failure", type = "image/png")
    public byte[] saveScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @AfterMethod

    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {

            System.out.println(" El test '" + result.getName() + "' fail.");
            System.out.println(" allure no working with java version.");
        }

        if (driver != null) {
            driver.quit();
        }
    }


}