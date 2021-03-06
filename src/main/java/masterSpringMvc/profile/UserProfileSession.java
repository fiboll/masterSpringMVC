package masterSpringMvc.profile;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

/**
 * Created by private on 14.02.18.
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserProfileSession implements Serializable {

	private static final long serialVersionUID = 4467604753094477724L;
	private String twitterHandle;
    private String email;
    private LocalDate birthDate;
    private List<String> tastes;
    private URL picturePath;
    
    public void setPicturePath(Resource picturePath) throws IOException {
    	this.picturePath = picturePath.getURL();
    }
    public Resource getPicturePath() {
	    return picturePath == null ? null : new  UrlResource(picturePath);
    }

    public void saveForm(ProfileForm profileForm) {
        this.twitterHandle = profileForm.getTwitterHandle();
        this.email = profileForm.getEmail();
        this.birthDate = profileForm.getBirthDate();
        this.tastes = profileForm.getTastes();
    }

    public ProfileForm toForm() {
        ProfileForm profileForm = new ProfileForm();
        profileForm.setTwitterHandle(twitterHandle);
        profileForm.setEmail(email);
        profileForm.setBirthDate(birthDate);
        profileForm.setTastes(tastes);
        return profileForm;
    }

}
