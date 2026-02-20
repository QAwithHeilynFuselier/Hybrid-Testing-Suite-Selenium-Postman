package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage  extends BasePage {

    @FindBy(css = "img.avatar")
    private WebElement userAvatarIcon;

    @FindBy(css = "[data-testid='btn-logout']")
    private WebElement logoutButton;
    @FindBy(css = "button[type='submit']")
    WebElement loginButton;

    @FindBy(css = ".name")
    private WebElement profileName;

    @FindBy(css = ".btn-submit")
    private WebElement saveButton;

    @FindBy(id = "inputProfileNewPassword")
    private WebElement newPasswordInput;

    @FindBy(id = "inputProfileCurrentPassword")
    private WebElement currentPasswordInput;
    @FindBy(id = "inputProfileName")
    private WebElement profileNameInput;

    @FindBy(id = "inputProfileEmail")
    private WebElement profileEmailInput;

    @FindBy(xpath = "//*[contains(text(), 'updated')]")
    private WebElement successMessage;

    private WebDriverWait wait;

    public HomePage(WebDriver givenDriver) {
        super(givenDriver);

        this.wait = new WebDriverWait(givenDriver, Duration.ofSeconds(10));
        PageFactory.initElements(givenDriver, this);
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red'", logoutButton);
        logoutButton.click();
    }




    public boolean isLogoutButtonVisible() {
        return logoutButton.isDisplayed();
    }

    public void highlightLogoutButton2() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red'", logoutButton);
        js.executeScript("arguments[0].style.backgroundColor='lavenderblush'", logoutButton);
    }
    public void highlightLogoutButton() {

        wait.until(ExpectedConditions.visibilityOf(logoutButton));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red'", logoutButton);
        js.executeScript("arguments[0].style.backgroundColor='lavenderblush'", logoutButton);
    }
    public void updateProfileInfo2(String updateName, String updateEmail){


        WebElement nameField = wait.until(ExpectedConditions.visibilityOf(profileNameInput));
        nameField.clear();
        nameField.sendKeys(updateName);
        WebElement emailField = wait.until(ExpectedConditions.visibilityOf(profileEmailInput));
        emailField.clear();
        emailField.sendKeys(updateEmail);


    }


    public void updateProfileInfo(String updateName, String updateEmail){
        wait.until(ExpectedConditions.visibilityOf(profileNameInput)).clear();
        profileNameInput.sendKeys(updateName);
        wait.until(ExpectedConditions.visibilityOf(profileEmailInput)).clear();
        profileEmailInput.sendKeys(updateEmail);
    }
    public void clickLogoutJS() {

        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logoutButton);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.success")));
    }

    public void clickLogoutJSs() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='btn-logout']")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.success")));
    }
    public void updatePassword(String currentPass, String newPass){
        wait.until(ExpectedConditions.visibilityOf(currentPasswordInput));

        currentPasswordInput.clear();

        currentPasswordInput.sendKeys(currentPass);
        newPasswordInput.clear();
        newPasswordInput.sendKeys(newPass);
        saveButton.click();

    }

    public void updateclicklogout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[data-testid='btn-logout']")));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", logoutButton);
    }


    public boolean isSuccessToastDisplayed() {
        try {

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.success")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isAvatarVisible() {
        try {

            return wait.until(ExpectedConditions.visibilityOf(userAvatarIcon)).isDisplayed();
        } catch (TimeoutException e) {

            return false;
        }
    }
    public WebElement getLoginButton()
    { return loginButton;
    }

    public void clickAvatar() {

        WebElement avatar = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("img.avatar")));


        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", avatar);
    }



}








