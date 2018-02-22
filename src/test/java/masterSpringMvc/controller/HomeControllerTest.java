package masterSpringMvc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import masterSpringMvc.profile.ProfileForm;
import masterSpringMvc.profile.UserProfileSession;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void shoudRedirectToProfile() throws Exception {
		this.mockMvc.perform(get("/"))
			.andDo(print())
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/profile"));
	}
	
	@Test
	public void shouldRedirectToTastes() throws Exception {
		MockHttpSession session = new MockHttpSession();
		ProfileForm profileForm = new ProfileForm();
		profileForm.setTastes(Arrays.asList("spring", "groovy"));
		UserProfileSession userProfileSession = new UserProfileSession();
		userProfileSession.saveForm(profileForm);
		
		session.setAttribute("scopedTarget.userProfileSession", userProfileSession);
		
		mockMvc.perform(get("/").session(session))
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/search/mixed;keywords=spring,groovy"));

	}

}
