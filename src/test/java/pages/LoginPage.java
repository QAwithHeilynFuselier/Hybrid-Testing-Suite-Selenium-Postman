package pages;

import org.openqa.selenium.*;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class   LoginPage extends BasePage {


    @FindBy(css = "[type='email']")
    private WebElement emailField;

    @FindBy(css = "[type='password']")
    private WebElement passwordField;

    @FindBy(css = "[type='submit']")
    private WebElement submitButtonLocator;

    @FindBy(css = ".error")
    private WebElement errorMessageLabel;


    @FindBy(css = "div.error")
    private WebElement errorBanner;

    public LoginPage(WebDriver givenDriver) {
        super(givenDriver);
    }


    public LoginPage provideEmail(String email) {
        findElement(emailField).sendKeys(email);
        return this;
    }

    public LoginPage providePassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    public LoginPage clickSubmitBtn() {
        click(submitButtonLocator);
        return this;
    }


    public String getErrorMessage2() {
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.error")));
            return alert.getText().trim();
        } catch (TimeoutException e) {

            return "ERROR: The error banner (.error) did not appear!";
        }
    }


    public String getHtml5ValidationMessage(WebElement element) {
        return element.getAttribute("validationMessage");
    }



    public String getErrorMessage() {
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(errorBanner));

            return errorBanner.getText().trim();
        } catch (TimeoutException e) {
            return "ERROR: The error banner (.error) did not appear!";
        }
    }



    public String getPasswordValidationMessage() {

        return passwordField.getAttribute("validationMessage");
    }


    public String getEmailValidationMessage() {
        return emailField.getAttribute("validationMessage");
    }



    public HomePage login(String email, String password) {
        return provideEmail(email)
                .providePassword(password)
                .clickSubmitBtn()
                .navigateToHome();
    }

    private HomePage navigateToHome() {
        return new HomePage(driver);
    }


}











