package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InfoPanelPage extends BasePage {
    /*elements<button aria-controls="extraPanelLyrics" id="extraTabLyrics" role="tab">
    Lyrics</button>*/
    /*<button aria-controls="extraPanelArtist" id="extraTabArtist" role="tab" aria-selected="true">Artist*/

       /* </button><button aria-controls="extraPanelAlbum" id="extraTabAlbum" role="tab">
    Album   </button>*/

    /*<button data-v-9c963a5a="" title="View song information" data-testid="toggle-extra-panel-btn" class="control text-uppercase">Info</button>*/
    /*<button title="Shuffle all songs by Unknown Artist" class="shuffle control"><i class="fa fa-random"></i></button>*/
    /*<button title="Shuffle all songs by Unknown Artist" class="shuffle control"><i class="fa fa-random"></i></button>*/
    @FindBy(css = "[data-testid='toggle-extra-panel-btn']")
    private WebElement infoButton;

    @FindBy(css = "#extra")
    private WebElement infoPanelContainer;

    @FindBy(css = "#extraTabLyrics")
    private WebElement lyricsTab;

    @FindBy(css = "#extraTabArtist")
    private WebElement artistTab;

    @FindBy(css = "#extraTabAlbum")
    private WebElement albumTab;


    @FindBy(css = "button[title*='Shuffle all songs by']")
    private WebElement artistShuffleBtn;

    @FindBy(css = "button[title*='Shuffle all songs in']")
    private WebElement albumShuffleBtn;


    @FindBy(css = "[data-test='artist-info'] .name")
    private WebElement artistName;
    @FindBy(css = "#extraPanelAlbum h1.name")
    private WebElement albumNameLabel;

    @FindBy(css = "#extraPanelAlbum .cover")
    private WebElement albumCoverElement;

    @FindBy(css = "#extraPanelLyrics [data-v-f3a790c2]")
    private WebElement lyricsContent;


    public InfoPanelPage(WebDriver givenDriver) {
        super(givenDriver);

    }
    public void ensurePanelIsClosed() {

        if (verifyFullInfoPanel()) {
            clickinfobuttonHidden();
            System.out.println("Closing panel ");
        }
    }

    public void clickinfobuttonHidden() {

        if (infoPanelContainer.isDisplayed()) {
            click(infoButton);
            System.out.println("Panel was open. Clicking to hide it.");
        } else {
            System.out.println("Panel is already hidden.");
        }

    }

    public void clickPanelIsVisible() {

        if (!infoPanelContainer.isDisplayed()) {
            click(infoButton);
            System.out.println("Panel was hidden. Clicking to show it.");
        } else {
            System.out.println("Panel is already visible.");
        }
    }

    public void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }


    public void clickLyricsTab() {

        highlightElement(lyricsTab);
        click(lyricsTab);
        wait.until(ExpectedConditions.visibilityOf(lyricsTab));
        System.out.println("Clicked on the Lyrics Tab.");
    }

    public void clickArtistTab() {
        highlightElement(artistTab);
        click(artistTab);
        wait.until(ExpectedConditions.visibilityOf(artistTab));
        System.out.println("Clicked on the ArtistTab.");
    }

    public void clickAlbumTab() {
        highlightElement(albumTab);
        click(albumTab);
        wait.until(ExpectedConditions.visibilityOf(albumTab));
        System.out.println("Clicked on the Album Tab.");
    }


    public boolean verifyFullInfoPanel() {
        try {
            clickPanelIsVisible();
            if (infoPanelContainer.isDisplayed()) {
                clickLyricsTab();
                clickArtistTab();
                clickAlbumTab();
                return true;
            } else {
                System.out.println("Could not verify info because the panel is hidden.");
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public void shuffleByArtist() {
        clickArtistTab(); // Ensure the "Artist" context is loaded
        click(artistShuffleBtn);
        System.out.println("Action: Shuffled the current Artist's songs.");
    }

    public void shuffleByAlbum() {
        clickAlbumTab(); // Ensure the "Album" context is loaded
        click(albumShuffleBtn);
        System.out.println("Action: Shuffled the current Album's songs.");
    }

    public void verifyArtistInInfoPanel() {
        clickArtistTab();

        String name = artistName.getText().trim();
        //code use for album test case
        if (name.toLowerCase().contains("unknown artist") || name.isEmpty()) {
            System.out.println("Name Artist:'Unknown Artist'.");
        } else {
            System.out.println("Artist Name -> " + name);
        }
    }

    public void verifyAlbumNameInPanel() {
        clickAlbumTab();
        String name = albumNameLabel.getText().trim();

        //code album story
        if (name.isEmpty() || name.toLowerCase().contains("unknown album")) {
            System.out.println(" Album no name assign 'Unknown Album'.");
        } else {
            System.out.println("Album Name" + name);
        }

        String style = albumCoverElement.getAttribute("style");
        if (style != null && style.contains("unknown-album.png")) {
            System.out.println("Album without cover (unknown-album.png)");
        } else if (style != null && style.contains("url")) {
            System.out.println("Album with cover");
        }


    }


    public void verifyLyricsDisplay() {

        clickLyricsTab();
        String lyricsText = lyricsContent.getText().trim();
        if (lyricsText.contains("No lyrics available") || lyricsText.contains("Bach")) {
            System.out.println(" No lyrics available for this track (Bach message detected).");
        }

        else if (lyricsText.isEmpty()) {
            System.out.println("The lyrics panel is completely empty.");
        }

        else {
            System.out.println("Success: Lyrics are displayed correctly.");
        }


    }

    public void validateFullInfoPanel() {
        System.out.println("--- Starting Info Panel Validation ---");
        System.out.println("Artist name should be displayed for a playing song");

            verifyArtistInInfoPanel();
        System.out.println("Album name and cover should be displayed for a playing song");
           verifyAlbumNameInPanel();
        System.out.println("Lyrics should be displayed for a playing song");
             verifyLyricsDisplay();

        System.out.println("--- Validation Complete ---");
    }

}

