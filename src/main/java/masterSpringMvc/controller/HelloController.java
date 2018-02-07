package masterSpringMvc.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HelloController {
	
	@Autowired
	private Twitter twitter;

	@RequestMapping("/")
	public String hello() {
		return "searchPage";
	}
	
	@RequestMapping("/result")
	public String twitter(@RequestParam (defaultValue = "TajnikiSpringMVC4") String search,
			Model model ) {
			SearchResults searchResult = twitter.searchOperations().search(search);
		
			model.addAttribute("tweets", searchResult.getTweets());
			model.addAttribute("search", search);
			
			return "tweets";
	}
	
	@RequestMapping(value = "/postSearch", method = RequestMethod.POST)
	public String postSearch(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String search = request.getParameter("search");
		
		if (search.toLowerCase().contains("śmieci")) {
			redirectAttributes.addFlashAttribute("error", "Spróbuj wpisać spring");
			return "redirect:/";
		}
		redirectAttributes.addAttribute("search", search);
		return "redirect:result";
	}

}
