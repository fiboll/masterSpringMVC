package masterSpringMvc.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by private on 03.03.18.
 */
public class LoginPage extends FluentPage {

    @FindBy(name =  "twitterSignin")
    FluentWebElement signInbutton;

    public String getUrl() {
        return "/login";
    }

    public void isAt() {
        assertThat(findFirst("h2").getText()).isEqualTo("Logowanie");
    }

    public void login() {
        signInbutton.click();
    }
}
