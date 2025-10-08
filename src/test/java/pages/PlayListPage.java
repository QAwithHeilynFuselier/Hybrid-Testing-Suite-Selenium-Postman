package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PlayListPage extends BasePage {


   // <a href="#!/queue" class="queue active">Current Queue</a>
    @FindBy(css = "#!/queue")
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

    public void OpenFirstPlaylist() {
        click(firstPlaylist);
        System.out.println("Clicked on the very first playlist in the list.");
    }




    public void CreateNewPlayList(){

        //<li data-v-5f33db96="" data-testid="playlist-context-menu-create-simple">New Playlist</li>



    }


}
