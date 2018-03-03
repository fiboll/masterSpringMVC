package masterSpringMvc.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.*;
/**
 * Created by private on 03.03.18.
 */
public class ProfilePage extends FluentPage {

    @FindBy(name =  "addTaste")
    FluentWebElement addTasteButton;

    @FindBy(name =  "save")
    FluentWebElement saveButton;

    public void isAt() {
        assertThat(findFirst("h2").getText()).isEqualTo("Twój profil");
    }

    public void fillInfos(String twitterHandle, String email, String birthDate) {
        fill("#twitterHandle").with(twitterHandle);
        fill("#email").with(email);
        fill("#birthDate").with(birthDate);
    }

    public void addTaste(String taste) {
        addTasteButton.click();
        fill("#tastes0").with(taste);
    }

    public void saveProfile() {
        saveButton.click();
    }
}
