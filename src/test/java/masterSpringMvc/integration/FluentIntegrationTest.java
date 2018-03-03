package masterSpringMvc.integration;


 import masterSpringMvc.MasterSpringMvcApplication;
import masterSpringMvc.search.StubTwitterSearchConfig;
 import org.fluentlenium.adapter.FluentTest;
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

    @Test
    public void hasPageTitle() {
        goTo("/");
        assertThat(findFirst("h2").getText()).isEqualTo("Logowanie");
        Assert.assertEquals("Logowanie", findFirst("h2").getText());
    }

    @Test
    public void shouldBeRedirectedAfterFillingForm() {
        goTo("/");
        takeScreenShot("loginForm.jpg");
        find("button", withName("twitterSignin")).click();
        assertThat(findFirst("h2").getText()).isEqualTo("Twój profil");
        fill("#twitterHandle").with("programista");
        fill("#email").with("programista@adrespoczty.pl");
        fill("#birthDate").with("1987-03-19");
        find("button", withName("addTaste")).click();
        fill("#tastes0").with("spring");
        find("button", withName("save")).click();

        takeScreenShot();

        assertThat(findFirst("h2").getText()).isEqualTo("Wyniki wyszukiwania spring");
        assertThat(findFirst("ul.collection").find("li")).hasSize(2);
    }

}
