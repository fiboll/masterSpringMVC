package masterSpringMvc.integration;


 import masterSpringMvc.MasterSpringMvcApplication;
 import masterSpringMvc.page.LoginPage;
 import masterSpringMvc.page.ProfilePage;
 import masterSpringMvc.page.SearchResultPage;
 import masterSpringMvc.search.StubTwitterSearchConfig;
 import org.fluentlenium.adapter.FluentTest;
 import org.fluentlenium.core.annotation.Page;
 import org.junit.Assert;
 import org.junit.Test;
 import org.junit.runner.RunWith;
 import org.openqa.selenium.WebDriver;
 import org.openqa.selenium.phantomjs.PhantomJSDriver;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.boot.test.context.SpringBootTest;
 import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;
/**
 * Created by private on 24.02.18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MasterSpringMvcApplication.class, StubTwitterSearchConfig.class },
        webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FluentIntegrationTest extends FluentTest {
    @Value("${local.server.port}")
    private int serverPort;

    @Override
    public WebDriver getDefaultDriver() {
        return new PhantomJSDriver();
    }

    @Override
    public String getDefaultBaseUrl() {
        return "http://localhost:" + serverPort;
    }

    @Page
    private LoginPage loginPage;

    @Page
    private ProfilePage profilePage;

    @Page
    private SearchResultPage searchResultPage;

    @Test
    public void hasPageTitle() {
        goTo("/");
        loginPage.isAt();
    }

    @Test
    public void shouldBeRedirectedAfterFillingForm() {
        goTo("/");
        loginPage.isAt();
        loginPage.login();

        profilePage.isAt();

        profilePage.fillInfos("programista", "programista@adrespoczty.pl","1987-03-19");
        profilePage.addTaste("spring");
        profilePage.saveProfile();

        //takeScreenShot();
        searchResultPage.isAt("spring");
        assertThat(searchResultPage.getNumberOfResults()).isEqualTo(2);
    }

}
