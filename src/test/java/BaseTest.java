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

        this.url = baseUrl;
        launchBrowser(this.url, browser, gridUrl);
    }

    public void launchBrowser(String baseUrl, String browser, String gridUrl) {
        // 1. LIMPIEZA DE DATOS (Evita que se caiga por valores null)
        String finalBaseUrl = (System.getProperty("baseUrl") != null) ? System.getProperty("baseUrl") : baseUrl;
        if (finalBaseUrl == null || finalBaseUrl.isEmpty()) {
            finalBaseUrl = "https://qa.koel.app/"; // Valor de respaldo absoluto
        }

        String finalGridUrl = (System.getProperty("gridUrl") != null) ? System.getProperty("gridUrl") : gridUrl;

        // 2. CONFIGURACIÓN DE OPCIONES
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");

        // Forzamos Headless en GitHub Actions para estabilidad
        chromeOptions.addArguments("--headless=new");

        try {
            // 3. DECISIÓN INTELIGENTE (Aquí es donde evitamos que se caiga)
            // Si gridUrl es null, vacío o es localhost, saltamos al modo LOCAL
            if (finalGridUrl != null && !finalGridUrl.isEmpty() && !finalGridUrl.contains("localhost")) {
                System.out.println("🚀 Intentando GRID en: " + finalGridUrl);

                ClientConfig config = ClientConfig.defaultConfig()
                        .connectionTimeout(Duration.ofMinutes(2))
                        .readTimeout(Duration.ofMinutes(2));

                driver = RemoteWebDriver.builder()
                        .address(new URL(finalGridUrl))
                        .oneOf(chromeOptions)
                        .config(config)
                        .build();
            } else {
                System.out.println("💻 Modo LOCAL detectado (o localhost ignorado)");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions);
            }

            // 4. FINALIZACIÓN
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            driver.get(finalBaseUrl);

        } catch (Exception e) {
            System.err.println("❌ ERROR CRÍTICO en launchBrowser: " + e.getMessage());
            // Si falla el Grid por cualquier razón, intentamos un último respaldo local
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(chromeOptions);
            driver.get(finalBaseUrl);
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