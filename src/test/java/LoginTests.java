
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.*;

import utils.SqlManager;
import utils.SqlQueries;

import java.sql.SQLException;
import java.time.Duration;


public class LoginTests extends BaseTest {
   @BeforeMethod
   @Parameters({"BaseUrl", "browser", "gridUrl"})

    public void setup(String BaseUrl, String browser, String gridUrl) {
       launchBrowser(BaseUrl, browser, gridUrl);
       navigatetoPage();

   }


   @Test(priority = 1, description ="INTERNSHIP-108264 Koel app | Info panel (Lyrics, Artist, Album)")

  public void verifyInfoPanelAndNavigation() {

      LoginPage loginPage = new LoginPage(driver);
        AllsongsPage clickAllSongs = new AllsongsPage(driver);
        InfoPanelPage panelPage = new  InfoPanelPage(driver);
        CurrentQueuePage currentsong = new CurrentQueuePage(driver);
    //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    loginPage.login("heilyn.fuselier@testpro.io", "NewYear@2026");
    wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("login")));

    String currentUrl = driver.getCurrentUrl();
       Assert.assertFalse(currentUrl.contains("404"), "The page loaded with a 404 error!");

        clickAllSongs.clickAllSongs();
       takeScreenshot("AllSongs");
        clickAllSongs.clickShufflebutton();
       takeScreenshot("Shuffle AllSongs");

    panelPage.clickPanelIsVisible();
    takeScreenshot("No show panel info  ");

        panelPage.clickinfobuttonHidden();
    takeScreenshot("Panel show for Button info ");

    takeScreenshot("Panel NO  visible");
    panelPage.clickPanelIsVisible();

        panelPage.validateFullInfoPanel();
    takeScreenshot("InfoPanel_info open ");

        panelPage.shuffleByArtist();
    takeScreenshot("shuffleArtist");
    panelPage.shuffleByAlbum();
    takeScreenshot("shuffleAlbum");


    panelPage.clickPanelIsVisible();
    currentsong.openCurrentQueue();
    takeScreenshot("Final_CurrentQueue");
    }



    @Test(priority = 2, description ="INTERNSHIP-108252 Koel app | Log in")

    public void loginwithcorrectPasswordTest() {
        LoginPage loginPage = new LoginPage(driver); // Page Factory init happens in the constructor
        HomePage homePage = loginPage.login("heilyn.fuselier@testpro.io", "NewYear@2026");

        Assert.assertTrue(homePage.isAvatarVisible(), "Avatar no visible");
        takeScreenshot("Login with correct password");
    }


    @DataProvider(name = "invalidLoginData")
    public Object[][] getInvalidLoginData() {
        return new Object[][] {
                {"missingAtSymbol", "Password123", "Please include an '@' in the email address. 'missingAtSymbol' is missing an '@'."},
               // {"heilyn.fuselier@testpro.io", "WrongPass", "Couldn't log you in"}, // BUG FOUND: Error banner does not appear for invalid credentials
                {"", "", "Please fill out this field."}
        };
    }


    @Test(priority = 3, dataProvider = "invalidLoginData")
    public void loginValidationMessages(String email, String pass, String expectedError) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.provideEmail(email);
        loginPage.providePassword(pass);
        loginPage.clickSubmitBtn();
        String internalMessage = loginPage.getEmailValidationMessage();
        String actualError = (internalMessage != null && !internalMessage.isEmpty())
                ? internalMessage
                : loginPage.getErrorMessage();
        takeScreenshot("Error_Login_" + email);
        Assert.assertTrue(actualError.contains(expectedError),
                "Expected [" + actualError + "] to contain [" + expectedError + "]");

    }
    @Test(priority = 4)

    public void loginWithDeepLink() {

        String deepLink = "https://qa.koel.app/#!/albums";
        driver.get(deepLink);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("heilyn.fuselier@testpro.io", "NewYear@2026");
        takeScreenshot("loginWithDeepLink");
        Assert.assertTrue(driver.getCurrentUrl().contains("#!/albums"));

    }



    //INTERNSHIP-108260: User should be able to log out after updating credentials

   @Test(priority = 5, description = "User should be able to log out after successful login, User should be navigated to the Login page after logging out")


   public void logoutAfterUpdateTest() {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        loginPage.login("heilyn.fuselier@testpro.io", "NewYear@2026");
        homePage.clickLogoutJS();
       takeScreenshot("Logout_Button");
      WebElement btn = wait.until(ExpectedConditions.visibilityOf(homePage.getLoginButton()));

      Assert.assertTrue(btn.isDisplayed(), "Error: Login button not visible. Logout failed.");

    }

    @Test(priority = 6, description = "Log student out’ button should be present on the homepage, next to ‘Profile’ button")

    public void logoutUIVerificationTest(){
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        loginPage.login("heilyn.fuselier@testpro.io", "NewYear@2026");
        homePage.highlightLogoutButton();
        takeScreenshot("logoutUIVerificationTest");
        boolean isVisible = homePage.isLogoutButtonVisible();
        Assert.assertTrue(isVisible, "Logout button should be visible next to the profile.");
    }


        @Test(priority = 7, description = "User should be able to log out after updating email and password")
        public void updateProfile() throws InterruptedException {
            HomePage homePage = new HomePage(driver);

            String name = "Heilyn Fuselier";
            String email = "heilyn.fuselier@testpro.io";
            LoginPage loginPage = new LoginPage(driver);

            loginPage.login("heilyn.fuselier@testpro.io", "NewYear@2026");
            homePage.clickAvatar();
            homePage.updateProfileInfo(name, email);
            homePage.updatePassword("NewYear@2026", "NewYear@2026");
            takeScreenshot("updateprofile");
            Assert.assertTrue(homePage.isSuccessToastDisplayed(),
                    "Expected success toast not shown");
            homePage.updateclicklogout(); // JS click

    }



    @Test(priority = 8, description = "Database-Driven Testing Dinámico")
    public void verifyArtistNameFromDatabase() throws SQLException {
        LoginPage loginPage = new LoginPage(driver);
        AllsongsPage allSongs = new AllsongsPage(driver);
        InfoPanelPage infoPanel = new InfoPanelPage(driver);

        loginPage.login("heilyn.fuselier@testpro.io", "NewYear@2026");
        allSongs.clickAllSongs();

        allSongs.clickShufflebutton();

        String currentSong = infoPanel.getSongTitleFromUI();

        String expectedArtist = SqlQueries.getArtistBySongTitle(currentSong);

        String actualArtist = infoPanel.getArtistFromInfoPanel();
        takeScreenshot("song playing");
        Assert.assertEquals(actualArtist, expectedArtist, "no data: " + currentSong);
    }

    @Test  (priority = 9, description = "renamePlaylistFlow")
    public void renamePlaylistFlow() {

        String newName = "Christmas";
        int userId = 29861;
        LoginPage loginPage = new LoginPage(driver);
        PlayListPage playListPage = new PlayListPage(driver);
        loginPage.login("heilyn.fuselier@testpro.io", "NewYear@2026");

        playListPage.OpenPlaylist();
        playListPage.rightClickPlaylist(3);
        playListPage.clickEditOption();

        playListPage.enterNewPlaylistName(newName);
        String dbName = SqlManager.getPlaylistNameFromDb(userId, newName);


        Assert.assertEquals(dbName, newName, "database is no working");
    }

}
