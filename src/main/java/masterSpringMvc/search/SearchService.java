package masterSpringMvc.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;

@Component
public class SearchService implements TwitterSearch {

	private Twitter twitter;

	@Autowired
	public SearchService(Twitter twitter) {
		this.twitter = twitter;
	}

	/* (non-Javadoc)
	 * @see masterSpringMvc.search.TwitterSearch#search(java.lang.String, java.util.List)
	 */
	@Override
	public List<Tweet> search(String searchType, List<String> keywords) {
		List<SearchParameters> searches = keywords.stream()
			.map(taste -> createSearchParam(searchType, taste))
			.collect(Collectors.toList());
		
		return searches.stream()
			.map(params -> twitter.searchOperations().search(params))
			.flatMap(searchResults -> searchResults.getTweets().stream())
			.collect(Collectors.toList());
	}
	
	private SearchParameters createSearchParam(String searchType, String taste) {
		SearchParameters.ResultType resultType = getResultType(searchType);
		SearchParameters searchParameters = new SearchParameters(taste);
		searchParameters.resultType(resultType);
		searchParameters.count(3);
		return searchParameters;
	}


	private SearchParameters.ResultType getResultType(String searchType) {
		for (SearchParameters.ResultType knownType : SearchParameters.ResultType.values()) {
			if (knownType.name().equalsIgnoreCase(searchType)) {
				return knownType;
			}
		}
		return SearchParameters.ResultType.RECENT;
	}

}
