package masterSpringMvc.controller;

import java.util.Arrays;

import org.springframework.mock.web.MockHttpSession;

import masterSpringMvc.profile.ProfileForm;
import masterSpringMvc.profile.UserProfileSession;

public class SessionBuilder {

	private final MockHttpSession session;
	private UserProfileSession sessionBean;
	ProfileForm profileForm = new ProfileForm();
	
	public SessionBuilder() {
		session = new MockHttpSession();
		profileForm = new ProfileForm();
		sessionBean = new UserProfileSession();
		session.setAttribute("scopedTarget.userProfileSession", sessionBean);
	}
	
	public SessionBuilder userTastes(String... tastes) {
		profileForm.setTastes(Arrays.asList(tastes));
		return this;
	}

	 public MockHttpSession build() {
		 sessionBean.saveForm(profileForm);
		 return session;
	 }

}
