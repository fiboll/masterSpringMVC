package masterSpringMvc.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import masterSpringMvc.search.cache.SearchCache;

@Service
@Profile("async")
public class ParallelSearchService implements TwitterSearch {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final AsyncSearch asyncSearch;

	@Autowired
	public ParallelSearchService(AsyncSearch asyncSearch) {
		super();
		this.asyncSearch = asyncSearch;
	}
	
	@Override
	public List<Tweet> search(String searchType, List<String> keywords) {
		CountDownLatch latch = new CountDownLatch(keywords.size());
		List<Tweet> allTweets = Collections.synchronizedList(new ArrayList<>());
		keywords
			.stream()
			.forEach(keyword -> asyncFetch(latch, allTweets, searchType, keyword));
		await(latch);
		return allTweets;

	}


	private void asyncFetch(CountDownLatch latch, List<Tweet> allTweets, String searchType, String keyword) {
		asyncSearch.asyncFetch(searchType, keyword)
			.addCallback(
				tweets -> onSuccess(allTweets, latch, tweets),
				ex -> onError(latch,ex));				
	}



	private void await(CountDownLatch latch) {
		try {
			latch.await();
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

	private void onSuccess(List<Tweet> results, CountDownLatch latch, List<Tweet> tweets) {
		results.addAll(tweets);
		latch.countDown();
	}

	private void onError(CountDownLatch latch, Throwable ex) {
		logger.error(ex, ex);
		latch.countDown();
	}

	@Component
	public static class AsyncSearch {
		protected final Log logger = LogFactory.getLog(getClass());
		private SearchCache searchCache;

		@Autowired
		public AsyncSearch(SearchCache searchCache) {
			this.searchCache = searchCache;
		}

		@Async
		public ListenableFuture<List<Tweet>> asyncFetch(String searchType,	String keyword) {
			logger.info(Thread.currentThread().getName() + " – Wyszukiwanie słowa " + keyword);
			return new AsyncResult<>(searchCache.fetch(searchType, keyword));
		}
	}

}
