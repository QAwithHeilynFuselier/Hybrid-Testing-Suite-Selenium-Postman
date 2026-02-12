package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PlayListPage extends BasePage {


   // <a href="#!/queue" class="queue active">Current Queue</a>
   @FindBy(css = "a[href='#!/queue']")
   private WebElement playlistSection;

    @FindBy(css = "#playlists li:nth-child(1)")
    private WebElement firstPlaylist;

    public PlayListPage(WebDriver givenDriver) {
        super(givenDriver);
    }



    public void OpenPlaylist() {
        click(playlistSection);
        System.out.println("Clicked on the playlist section.");
    }

    public void rightClickPlaylist(int index) {

        String selector = ".playlist:nth-child(" + index + ")";
        WebElement playlist = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));

        Actions actions = new Actions(driver);
        actions.contextClick(playlist).perform();
        System.out.println("Right-clicked playlist #" + index);
    }
    public void clickEditOption() {

        WebElement editButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'Edit')]")));

        editButton.click();
        System.out.println("Clicked on the Edit option.");
    }
    public void enterNewPlaylistName(String name) {
        WebElement editingPlaylist = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.playlist.editing")));

        WebElement renameInput = editingPlaylist.findElement(By.tagName("input"));
        renameInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);

        renameInput.sendKeys(name, Keys.ENTER);
        System.out.println("new name : " + name);
    }

}
