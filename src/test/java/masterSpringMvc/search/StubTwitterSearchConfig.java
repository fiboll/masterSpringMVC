package masterSpringMvc.search;

import java.util.Arrays;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

@Configuration
public class StubTwitterSearchConfig {

	@Primary
	@Bean
	public TwitterSearch twitterSearch() {
		
		Tweet tweet = new Tweet(0L, "", "Treść tweeta", null, "", "", null, 0L, "", "");
		tweet.setUser(new TwitterProfile(0l, "", "", "", "", "", "", new Date()));
		
		Tweet tweet2 = new Tweet(0L, "", "Treść innego tweeta", null, "", "", null, 0L, "", "");
		tweet2.setUser(new TwitterProfile(0l, "", "", "", "", "", "", new Date()));
		
		return (searchType, keywords) -> Arrays.asList(
			tweet, tweet2
		);
	}

	
}
