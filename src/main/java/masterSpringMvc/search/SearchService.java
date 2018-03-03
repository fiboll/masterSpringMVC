package masterSpringMvc.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;

import masterSpringMvc.search.cache.SearchCache;

@Component
@Profile("!async")
public class SearchService implements TwitterSearch {

	private SearchCache searchCache;
	@Autowired
	public SearchService(SearchCache searchCache) {
		this.searchCache = searchCache;
	}

	/* (non-Javadoc)
	 * @see masterSpringMvc.search.TwitterSearch#search(java.lang.String, java.util.List)
	 */
	@Override
	public List<Tweet> search(String searchType, List<String> keywords) {
		return keywords.stream()
				.flatMap(keyword -> searchCache.fetch(searchType, keyword).stream())
				.collect(Collectors.toList());
				
	}
}
