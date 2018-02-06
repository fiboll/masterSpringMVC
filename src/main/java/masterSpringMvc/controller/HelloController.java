package masterSpringMvc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
	
	@Autowired
	private Twitter twitter;

	@RequestMapping("/")
	public String hello(@RequestParam (defaultValue = "świecie") String name, Model model) {
		model.addAttribute("message", "Witaj, " + name + "!");
		return "resultPage";
	}
	
	@RequestMapping("/twitter")
	public String twitter(@RequestParam (defaultValue = "TajnikiSpringMVC4") String search,
			Model model ) {
			SearchResults searchResult = twitter.searchOperations().search(search);
			String text = searchResult.getTweets().get(0).getText();
			model.addAttribute("message", text);
			
			return "resultPage";
	}
}
