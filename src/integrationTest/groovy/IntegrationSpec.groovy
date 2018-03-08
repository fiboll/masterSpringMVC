import geb.Configuration
import geb.pages.LoginPage
import geb.pages.ProfilePage
import geb.pages.SearchResultPage
import geb.spock.GebSpec
import masterSpringMvc.MasterSpringMvcApplication
import masterSpringMvc.auth.StubSocialSigninConfig
import masterSpringMvc.search.StubTwitterSearchConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by private on 07.03.18.
 */
@ContextConfiguration(classes = [ MasterSpringMvcApplication, StubTwitterSearchConfig, StubSocialSigninConfig])
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationSpec extends GebSpec {

    @Value('${local.server.port}')
    int port

    Configuration createConf() {
        def configuration = super.createConf()
        configuration.baseUrl = "http://localhost:$port"
        configuration
    }

    def "Jeżeli użytkownik nie jest zalogowany, kierowany jest na stronę logowania"() {
        when: "Otwieram stronę główną"
        go '/'
        then: "Jestem kierowany na stronę logowania"
        $('h2', 0).text() == 'Logowanie'
    }

    def "Użytkownik podczas pierwszej wizyty jest kierowany na stronę profilu"() {
        when: 'Połączyłem się'
        to LoginPage
        loginWithTwitter()

        and: "Otwieram stronę główną"
        go '/'

        then: "Jestem kierowany na stronę profilu"
        $('h2').text() == 'Twój profil'
    }

    def "Po zdefiniowaniu profilu wyświetlane są wyniki odpowiadające jego preferencjom"() {
        given: 'Połączyłem się'
        to LoginPage
        loginWithTwitter()

        and: 'Jestem na stronie profilu’'
        to ProfilePage

        when: 'Wypełniam profil'
        fillInfos("programista", "programista@adrespoczty.pl", "1987-03-19");
        addTaste("spring")

        and: 'Zapisuję'
        saveProfile()

        then: 'Jestem przeniesiony na stronę z wynikami wyszukiwania'
        at SearchResultPage
        page.results.size() == 2
    }
}
