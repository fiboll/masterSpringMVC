package masterSpringMvc.search;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import masterSpringMvc.MasterSpringMvcApplication;
import masterSpringMvc.user.User;
import masterSpringMvc.user.UserRepository;
import masterSpringMvc.utils.JsonUtil;

import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MasterSpringMvcApplication.class, StubTwitterSearchConfig.class })
public class UserApiControllerTest {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		userRepository.reset(new User("robert@spring.io"));
	}
	
	@Test
	public void should_list_users() throws Exception {
		this.mockMvc.perform(get("/api/users")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].email", is("robert@spring.io")));
	}
	
	@Test
	public void shouldCreateNewUser() throws Exception {
		User user = new User("john@spring.io");
		mockMvc.perform(
			post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(user))
			)
			.andExpect(status().isCreated());
		
		assertThat(userRepository.findAll())
			.extracting(User::getEmail)
			.containsOnly("robert@spring.io", "john@spring.io");
				
	}
	
	@Test
	public void should_delete_user() throws Exception {
		this.mockMvc.perform(
		delete("/api/user/robert@spring.io")
		.accept(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isOk());
		assertThat(userRepository.findAll()).hasSize(0);
	}
	
	@Test
	public void should_return_not_found_when_deleting_unknown_user() throws Exception {
		this.mockMvc.perform(
		delete("/api/user/nie-znaleziony@mail.com")
		.accept(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void put_should_update_existing_user() throws Exception {
		User user = new User("nowy@spring.io");

		this.mockMvc.perform(put("/api/user/robert@spring.io")
				.content(JsonUtil.toJson(user))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		assertThat(userRepository.findAll()).extracting(User::getEmail).containsOnly("robert@spring.io");
	}

}
