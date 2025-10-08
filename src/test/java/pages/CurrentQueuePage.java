package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CurrentQueuePage extends BasePage{

    // <a href="#!/queue" class="queue active">Current Queue</a>
    @FindBy(css = "a[href='#!/queue']")
    private WebElement currentqueuesection;


    public CurrentQueuePage(WebDriver givenDriver) {
        super(givenDriver);
    }

    public void openCurrentQueue() {
        click(currentqueuesection);
        System.out.println("Clicked on the Current queue Section.");
    }


    public boolean isQueueVisible() {
        return driver.getCurrentUrl().contains("queue");
    }




}
