import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
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
        launchBrowser(this.url);

    }

    public void launchBrowser(String baseUrl, String browser,String gridUrl ) {

        this.url = baseUrl;
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");


        if (System.getenv("CHROME_HEADLESS") != null || System.getProperty("CHROME_HEADLESS") != null) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        } else {
        options.addArguments("--start-maximized");
    }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        try {
            driver = new RemoteWebDriver(new URL(gridUrl), options);


            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            actions = new Actions(driver);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            if (baseUrl != null) {
                driver.get(baseUrl);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("error url grid: " + e.getMessage());
        } catch (Exception e) {

            throw new RuntimeException("no connect to selenium grid " + gridUrl, e);
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