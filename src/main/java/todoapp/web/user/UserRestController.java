package todoapp.web.user;

import java.io.File;
import java.net.URI;

import javax.annotation.security.RolesAllowed;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import todoapp.core.user.application.ProfilePictureChanger;
import todoapp.core.user.domain.ProfilePicture;
import todoapp.core.user.domain.ProfilePictureStorage;
import todoapp.core.user.domain.User;
import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;
import todoapp.web.model.UserProfile;

@RolesAllowed(UserSession.ROLE_USER)
@RestController
public class UserRestController {
	
	private ProfilePictureChanger changer;
	private ProfilePictureStorage storage;
	
	private UserSessionRepository sessionRepository;

	public UserRestController(ProfilePictureChanger changer, ProfilePictureStorage storage,
			UserSessionRepository sessionRepository) {
		this.changer = changer;
		this.storage = storage;
		this.sessionRepository = sessionRepository;
	}

	@GetMapping("/api/user/profile")
	public UserProfile profile(UserSession session) {
		return new UserProfile(session.getUser());
	}
	
	@PostMapping("/api/user/profile-picture")
	public UserProfile profilePictureChange(UserSession session, MultipartFile profilePicture) {
//		profilePicture.getResource().getFile();
//		profilePicture.getInputStream();
//		profilePicture.transferTo(new File("/../../xx"));
		
		URI profilePictureUri = storage.save(profilePicture.getResource());
		ProfilePicture newProfilePicture = new ProfilePicture(profilePictureUri); 
		User savedUser = changer.change(session.getUser().getUsername(), newProfilePicture);
		
		sessionRepository.set(new UserSession(savedUser));
		
		return new UserProfile(session.getUser());
	}
	
	@GetMapping("/user/profile-picture")
	public Resource profilePicture(UserSession session) {
		URI uri = session.getUser().getProfilePicture().getUri();
		return storage.load(uri); 
	}
	
}
