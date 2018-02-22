package masterSpringMvc.search;

import java.util.List;

import org.springframework.social.twitter.api.Tweet;

public interface TwitterSearch {

	List<Tweet> search(String searchType, List<String> keywords);

}