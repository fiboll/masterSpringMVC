package masterSpringMvc.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import masterSpringMvc.profile.UserProfileSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import masterSpringMvc.date.USLocalDateFormatter;
import masterSpringMvc.profile.ProfileForm;

@Controller
public class ProfileController {

	private UserProfileSession userProfileSession;

	@Autowired
	public ProfileController(UserProfileSession userProfileSession) {
		this.userProfileSession = userProfileSession;
	}

	@ModelAttribute("dateFormat")
	public String localeFormat(Locale locale) {
	return USLocalDateFormatter.getPattern(locale);
	}

	@ModelAttribute
	public ProfileForm getProfileForm() {
		return userProfileSession.toForm();
	}

	@RequestMapping("/profile")
	public String displayProfile(ProfileForm profileForm) {
		return "profile/profilePage";
	}

	@RequestMapping(value = "/profile", params= {"save"}, method = RequestMethod.POST)
	public String saveProfile(@Valid ProfileForm profileForm, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "profile/profilePage";
		}
		
		System.out.println("pomyślnie zapisany profil " + profileForm);
		userProfileSession.saveForm(profileForm);
		return "redirect:/profile";
	}
	
	@RequestMapping(value = "/profile", params = {"addTaste"})
	public String addRow(ProfileForm profileForm) {
		profileForm.getTastes().add(null);
		return "profile/profilePage";
	}
	
	@RequestMapping(value = "/profile", params = {"removeTaste"})
	public String removeRow(ProfileForm profileForm, HttpServletRequest req) {
		Integer rowId = Integer.valueOf(req.getParameter("removeTaste"));
		profileForm.getTastes().remove(rowId.intValue());
		return "profile/profilePage";
	}

}
