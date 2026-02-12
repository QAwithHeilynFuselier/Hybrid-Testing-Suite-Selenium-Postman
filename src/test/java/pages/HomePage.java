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

    public HomePage(WebDriver givenDriver) {
        super(givenDriver);
        PageFactory.initElements(givenDriver, this);
    }

    public boolean isAvatarVisible() {
        return findElement(userAvatarIcon).isDisplayed();
    }
    public void clickAvatar() {
        wait.until(ExpectedConditions.elementToBeClickable(userAvatarIcon)).click();

    }
    public boolean isProfileVisible() {
        return profileName.isDisplayed();
    }


    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red'", logoutButton);
        logoutButton.click();
    }
    public WebElement getLoginButton() {
        return loginButton;
    }

    public boolean isLogoutButtonVisible() {
        return logoutButton.isDisplayed();
    }

    public void highlightLogoutButton() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red'", logoutButton);
        js.executeScript("arguments[0].style.backgroundColor='lavenderblush'", logoutButton);
    }

    public void updateProfileInfo(String updateName, String updateEmail){


        WebElement nameField = wait.until(ExpectedConditions.visibilityOf(profileNameInput));
        nameField.clear();
        nameField.sendKeys(updateName);
        WebElement emailField = wait.until(ExpectedConditions.visibilityOf(profileEmailInput));
        emailField.clear();
        emailField.sendKeys(updateEmail);


    }



    public void clickLogoutJS() {
        WebElement btn = driver.findElement(By.cssSelector("[data-testid='btn-logout']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.success")));

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
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector("div.success")
                    )
            );
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }







}








