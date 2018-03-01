package masterSpringMvc.search.cache;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.social.TwitterProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

import masterSpringMvc.search.SearchParametersBuilder;

@Service
public class SearchCache {
	protected final Log logger = LogFactory.getLog(getClass());
	private Twitter twitter;
	
	@Autowired
	public SearchCache(TwitterProperties twitterProperties) {
		twitter = new TwitterTemplate(twitterProperties.getAppId(), twitterProperties.getAppSecret());
	}

	@Cacheable("searches") 
	public List<Tweet> fetch(String searchType, String keyword) {
		logger.info("Słowa " + keyword + " nie ma w pamięci podręcznej.");

		SearchParameters searchParam = SearchParametersBuilder.createSearchParam(searchType, keyword);
		return twitter.searchOperations()
				.search(searchParam)
				.getTweets();
	}
}
