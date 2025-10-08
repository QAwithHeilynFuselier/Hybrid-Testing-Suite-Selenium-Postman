package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AllsongsPage extends BasePage{
    //<a href="#!/songs" class="songs active">All Songs</a>
    @FindBy(css = "a[href='#!/songs']")
    private WebElement goToAllSongsPage;

    @FindBy(css = "[data-test='btn-shuffle-all']")
    private WebElement shuffleAllButton;


    public AllsongsPage(WebDriver givenDriver) {
        super(givenDriver);
    }


    public void clickAllSongs(){
        click(goToAllSongsPage);
        System.out.println("Clicked on the all songs page Section.");

    }

    //click shuffle buttom

    /* <button data-v-263c0e07="" data-v-03986d65="" orange="" title="Shuffle all songs" data-test="btn-shuffle-all" class="btn-shuffle-all" data-v-59142d4a=""><i data-v-03986d65="" data-v-263c0e07="" class="fa fa-random"></i> All
            </button> */

    public void clickShufflebutton() {

        click(shuffleAllButton);
        System.out.println("Clicked on the Shuffle Button.");

    }







}

