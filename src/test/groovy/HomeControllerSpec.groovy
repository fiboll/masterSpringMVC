package masterSpringMvc.controller

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import spock.lang.Specification;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import masterSpringMvc.MasterSpringMvcApplication

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [ MasterSpringMvcApplication ])
class HomeControllerSpec {

	@Autowired
	WebApplicationContext wac;
	MockMvc mockMvc;
	
	@Before
	def void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	def void 'Użytkownik podczas pierwszej wizyty jest kierowany na stronę profilu'() {
		when:  "Otwieram stronę główną"
		def response = this.mockMvc.perform(get("/"))
		then: "Jestem kierowany na stronę profilu"
		response
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/profil2e"))
	}
}
