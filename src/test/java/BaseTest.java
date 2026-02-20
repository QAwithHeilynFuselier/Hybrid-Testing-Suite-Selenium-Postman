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

    public void launchBrowser(String baseUrl, String browser, String gridUrl) {
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
            if (finalGridUrl != null && !finalGridUrl.contains("localhost")) {
                System.out.println("DEBUG: Intentando conectar a la URL: [" + finalGridUrl + "]");
                driver = new RemoteWebDriver(new URL(finalGridUrl), options);
            } else {
                System.out.println("MODO LOCAL: Iniciando Chrome...");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);
            }


            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            this.actions = new Actions(driver);


            driver.get(baseUrl);

        } catch (Exception e) {
            throw new RuntimeException("Error al conectar: " + e.getMessage());
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