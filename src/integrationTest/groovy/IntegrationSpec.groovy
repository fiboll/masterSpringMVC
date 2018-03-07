import geb.Configuration
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
}
