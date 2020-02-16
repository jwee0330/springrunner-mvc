package todoapp.web.user;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import todoapp.security.UserSession;
import todoapp.web.model.UserProfile;

@RestController
public class UserRestController {

	@RolesAllowed(UserSession.ROLE_USER)
	@GetMapping("/api/user/profile")
	public UserProfile profile(UserSession session) {
		return new UserProfile(session.getUser());
	}
	
}