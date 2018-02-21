package masterSpringMvc.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import masterSpringMvc.authentication.AuthenticatingSignInAdapter;

@Controller
public class SignupController {
	
	private final ProviderSignInUtils signInUtils;

	@Autowired
	public SignupController(ConnectionFactoryLocator locator, UsersConnectionRepository usersConnectionRepository) {
		super();
		this.signInUtils = new ProviderSignInUtils(locator, usersConnectionRepository);
	}
	
	@RequestMapping(value = "/signup")
	public String signup(WebRequest webRequest) {
		Connection<?> connection = signInUtils.getConnectionFromSession(webRequest);
		
		if (connection != null) {
			AuthenticatingSignInAdapter.authenticate(connection);
			signInUtils.doPostSignUp(connection.getDisplayName(), webRequest);
		}
		
		return "redirect:/profile";
	}

}
