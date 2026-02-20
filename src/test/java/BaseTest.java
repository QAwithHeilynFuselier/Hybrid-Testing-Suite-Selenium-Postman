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

    public void launchBrowser1(String baseUrl, String browser, String gridUrl) {
        String systemGridUrl = System.getProperty("gridUrl");
        String finalGridUrl = (systemGridUrl != null && !systemGridUrl.isEmpty())
                ? systemGridUrl
                : gridUrl;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        if ("true".equals(System.getProperty("CHROME_HEADLESS"))) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }

        try {

            if (finalGridUrl != null && !finalGridUrl.isEmpty()) {
                System.out.println("DEBUG: Conectando a Selenium Grid en: [" + finalGridUrl + "]");
                driver = new RemoteWebDriver(new URL(finalGridUrl), options);
            } else {
                System.out.println("MODO LOCAL: Iniciando ChromeDriver local...");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);
            }

            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            this.actions = new Actions(driver);
            driver.get(baseUrl);

        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("Error crítico en launchBrowser: " + e.getMessage());
        }
    }

    public void dos2(String baseUrl, String browser, String gridUrl) {
        String systemGridUrl = System.getProperty("gridUrl");
        String finalGridUrl = (systemGridUrl != null && !systemGridUrl.isEmpty())
                ? systemGridUrl
                : gridUrl;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");


        options.setCapability("se:cdpEnabled", false);

        if ("true".equals(System.getProperty("CHROME_HEADLESS"))) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--remote-allow-origins=*");
            options.setCapability("se:cdpEnabled", false);
        }

        try {
            if (finalGridUrl != null && !finalGridUrl.isEmpty()) {
                System.out.println("DEBUG: Intentando conexión a: " + finalGridUrl);
                driver = new RemoteWebDriver(new URL(finalGridUrl), options);
            } else {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);
            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            this.actions = new Actions(driver);
            System.out.println("Navegando a: " + baseUrl);
            driver.get(baseUrl);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error crítico en launchBrowser: " + e.getMessage());
        }
    }


    public void launchBrowserww(String baseUrl, String browser, String gridUrl) {
        // 1. Prioridad: Si GitHub Actions envía la URL por comando (-DgridUrl), la usamos.
        String systemGridUrl = System.getProperty("gridUrl");

        // 2. Lógica inteligente: Si no hay propiedad de sistema Y no hay gridUrl manual, es Local.
        String finalGridUrl = (systemGridUrl != null && !systemGridUrl.isEmpty()) ? systemGridUrl : gridUrl;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");

        // Configuración para entornos Docker/Nube
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // 3. Manejo de Headless (Obligatorio para GitHub Actions)
        if ("true".equals(System.getProperty("CHROME_HEADLESS"))) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        try {
            // 4. Verificación de conexión: ¿Realmente queremos usar un Grid?
            if (finalGridUrl != null && !finalGridUrl.isEmpty()) {
                System.out.println("DEBUG: Intentando conexión al Grid en: " + finalGridUrl);

                // Usar el cliente JDK para evitar Timeouts en Java 21
                System.setProperty("webdriver.http.factory", "jdk-http-client");

                // TIP: Agregamos un timeout a la creación de la sesión para que no se congele
                driver = new RemoteWebDriver(new URL(finalGridUrl), options);
                System.out.println("INFO: Sesión creada exitosamente en el Grid.");
            } else {
                // 5. MODO LOCAL: Si falló lo anterior, abrimos el Chrome de tu PC
                System.out.println("MODO LOCAL: Iniciando ChromeDriver...");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            this.actions = new Actions(driver);

            System.out.println("Navegando a: " + baseUrl);
            driver.get(baseUrl);

        } catch (Exception e) {
            System.err.println("CAUSA DEL FALLO: " + e.getMessage());
            // Importante para ver el error real en la consola de IntelliJ
            throw new RuntimeException("Error crítico en launchBrowser: " + e.getMessage(), e);
        }
    }

    public void launchBrowser(String baseUrl, String browser, String gridUrl) {
        // 1. Prioridad: Propiedad de sistema (GitHub Actions), luego parámetro manual, si no, vacío.
        String systemGridUrl = System.getProperty("gridUrl");
        String finalGridUrl = (systemGridUrl != null && !systemGridUrl.isEmpty()) ? systemGridUrl : gridUrl;

        ChromeOptions options = new ChromeOptions();

        // Configuración de estabilidad para Docker/Linux
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // 2. Manejo de Headless
        if ("true".equals(System.getProperty("CHROME_HEADLESS"))) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        try {
            if (finalGridUrl != null && !finalGridUrl.isEmpty()) {
                System.out.println("DEBUG: Conectando a Selenium Grid: " + finalGridUrl);

                // IMPORTANTE: En Selenium 4.28.1 + Java 21, no necesitas forzar "jdk-http-client"
                // ya que es el estándar por defecto. Solo asegúrate de manejar el URL correctamente.

                driver = new RemoteWebDriver(new URL(finalGridUrl), options);
                System.out.println("INFO: Sesión remota iniciada.");
            } else {
                System.out.println("MODO LOCAL: Iniciando ChromeDriver...");
                // Usando el Selenium Manager interno de Selenium 4.28+ (ya no necesitas WebDriverManager obligatoriamente)
                driver = new ChromeDriver(options);
            }

            // 3. Timeouts robustos
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Un poco más de margen para el Grid
            this.actions = new Actions(driver);

            System.out.println("Navegando a: " + baseUrl);
            driver.get(baseUrl);

        } catch (Exception e) {
            System.err.println("❌ ERROR EN LAUNCHBROWSER: " + e.getMessage());
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