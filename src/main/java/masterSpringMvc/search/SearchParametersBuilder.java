package masterSpringMvc.search;

import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.stereotype.Component;

@Component
public class SearchParametersBuilder {

	
	public static SearchParameters createSearchParam(String searchType, String taste) {
		SearchParameters.ResultType resultType = getResultType(searchType);
		SearchParameters searchParameters = new SearchParameters(taste);
		searchParameters.resultType(resultType);
		searchParameters.count(3);
		return searchParameters;
	}


	private static SearchParameters.ResultType getResultType(String searchType) {
		for (SearchParameters.ResultType knownType : SearchParameters.ResultType.values()) {
			if (knownType.name().equalsIgnoreCase(searchType)) {
				return knownType;
			}
		}
		return SearchParameters.ResultType.RECENT;
	}
}
