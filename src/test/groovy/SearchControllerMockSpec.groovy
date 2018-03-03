package masterSpringMvc.controller

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import masterSpringMvc.MasterSpringMvcApplication
import masterSpringMvc.search.LightTweet
import masterSpringMvc.search.SearchController
import masterSpringMvc.search.TwitterSearch
import spock.lang.Specification

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [ MasterSpringMvcApplication ])
class SearchControllerMockSpec extends Specification {
	
	
	def twitterSearch
	def searchController
	def mockMvc
	
	@Before
	void before() {
		twitterSearch = Mock(TwitterSearch.class)
		searchController = new SearchController(twitterSearch)
		mockMvc = MockMvcBuilders.standaloneSetup(searchController)
			.setRemoveSemicolonContent(false)
			.build()
	}
	
	@Test
	def "Po wyszukaniu słowa spring powinna pojawić się strona z wynikami"() {
		when: "Szukam słowa spring"
		def response = mockMvc.perform(get("/search/mixed;keywords=spring"))
		then: "Usługa wyszukująca jest wywoływana tylko raz"
		1 * twitterSearch.search(_, _) >> [
			new LightTweet('Treść tweeta')
		]
		and: "Pojawiła się strona z wynikami"
		response
				.andExpect(status().isOk())
				.andExpect(view().name("tweets"))
		and: "Model danych zawiera znalezione tweety"
		response
				.andExpect(model().attribute("tweets", everyItem(
				hasProperty("text", is("Treść tweeta"))
				)))
	}
		
}
