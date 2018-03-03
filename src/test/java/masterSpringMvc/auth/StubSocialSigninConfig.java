package masterSpringMvc.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by private on 03.03.18.
 */
@Configuration
public class StubSocialSigninConfig {
    @Bean
    @Primary
    @Autowired
    public ProviderSignInController signInController(ConnectionFactoryLocator factoryLocator,
                                                     UsersConnectionRepository usersRepository,
                                                     SignInAdapter signInAdapter) {
        return new FakeSigninController(factoryLocator, usersRepository,signInAdapter);
    }

    public class FakeSigninController extends ProviderSignInController {


        public FakeSigninController(ConnectionFactoryLocator factoryLocator, UsersConnectionRepository usersRepository, SignInAdapter signInAdapter) {
            super(factoryLocator, usersRepository, signInAdapter);
        }

        @Override
        public RedirectView signIn(@PathVariable String providerId, NativeWebRequest request) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("programista", null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new RedirectView("/");
        }
    }
}
