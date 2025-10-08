import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class LogoffTest extends BaseTest {
    @BeforeMethod
    @Parameters({"BaseUrl"})
    public void setup(String BaseUrl) {
        launchBrowser(BaseUrl);
    }

    @Test(priority = 1, description = "Log out success")
    public void logoutSuccessTest() {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        loginPage.login("heilyn.fuselier@testpro.io", "NewPassword@2026");
       // homePage.clickAvatar();

        takeScreenshot("Logout_Button_Visible");

        homePage.clickLogout();

        boolean isRedirectioned = wait.until(ExpectedConditions.urlContains("login"));
        Assert.assertTrue(isRedirectioned, "continue: " + driver.getCurrentUrl());
        takeScreenshot("Logout_Redirection_Success");

    }
    @Test(priority = 2, description = "Verify logout after credential update")
    public void logoutAfterUpdateTest() {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        loginPage.login("heilyn.fuselier@testpro.io", "NewPassword@2026");
        homePage.clickLogout();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

}