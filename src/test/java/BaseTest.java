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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import java.net.URL;
import java.net.MalformedURLException;
import org.testng.annotations.Parameters;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Duration;
import org.openqa.selenium.remote.http.ClientConfig;

public class BaseTest {

    protected String url;
  protected WebDriver driver;
    protected WebDriverWait wait;
   protected Actions actions;


    @BeforeSuite
    @Parameters({"BaseUrl", "browser", "gridUrl"})

    public void setUp(String baseUrl,String browser, String gridUrl) {
        this.url = baseUrl;
        launchBrowser(baseUrl, browser, gridUrl);

    }

    public void launchBrowser(String baseUrl, String browser, String gridUrl) {
        // 1. Obtener URL del Grid (Prioridad GitHub Actions)
        String systemGridUrl = System.getProperty("gridUrl");
        String finalGridUrl = (systemGridUrl != null && !systemGridUrl.isEmpty()) ? systemGridUrl : gridUrl;

        ChromeOptions options = new ChromeOptions();

        // Configuración esencial para estabilidad en Docker
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        if ("true".equals(System.getProperty("CHROME_HEADLESS"))) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
        } else {
            options.addArguments("--start-maximized");
        }

        try {
            if (finalGridUrl != null && !finalGridUrl.isEmpty()) {
                System.out.println("DEBUG: Conectando a Selenium Grid en: " + finalGridUrl);

                // --- SOLUCIÓN AL TIMEOUT (LÍNEA 211) ---
                // Creamos una configuración de cliente con más tiempo de espera (3 minutos)
                // Esto evita el java.util.concurrent.TimeoutException que viste en el log
                org.openqa.selenium.remote.http.ClientConfig config =
                        org.openqa.selenium.remote.http.ClientConfig.defaultConfig()
                                .readTimeout(Duration.ofMinutes(3))
                                .connectionTimeout(Duration.ofMinutes(3));

                // Usamos el Builder para pasarle la URL, las opciones y la nueva configuración
                driver = RemoteWebDriver.builder()
                        .address(new URL(finalGridUrl))
                        .oneOf(options)
                        .config(config)
                        .build();

                System.out.println("INFO: Sesión remota iniciada exitosamente.");
            } else {
                System.out.println("MODO LOCAL: Iniciando ChromeDriver...");
                driver = new ChromeDriver(options);
            }

            // Timeouts de interacción
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Más margen para el Grid
            this.actions = new Actions(driver);

            System.out.println("Navegando a: " + baseUrl);
            driver.get(baseUrl);

        } catch (Exception e) {
            System.err.println("❌ ERROR CRÍTICO EN LAUNCHBROWSER: " + e.getMessage());
            // El "trick" aquí es lanzar el error con 'e' al final para ver la causa raíz
            throw new RuntimeException("Fallo al inicializar el driver: " + e.getMessage(), e);
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