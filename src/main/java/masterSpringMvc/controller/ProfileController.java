package masterSpringMvc.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import masterSpringMvc.date.USLocalDateFormatter;
import masterSpringMvc.profile.ProfileForm;

@Controller
public class ProfileController {
	
	@ModelAttribute("dateFormat")
	public String localeFormat(Locale locale) {
	return USLocalDateFormatter.getPattern(locale);
	}


	@RequestMapping("/profile")
	public String displayProfile(ProfileForm profileForm) {
		return "profile/profilePage";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String saveProfile(@Valid ProfileForm profileForm, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "profile/profilePage";
		}
		
		System.out.println("pomyślnie zapisany profil " + profileForm);
		return "redirect:/profile";
	}

}
