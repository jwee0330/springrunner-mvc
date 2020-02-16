package todoapp.web.user;

import javax.annotation.security.RolesAllowed;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;

@Controller
public class LogoutController {
	 private UserSessionRepository sessionRepository;
	 
	 public LogoutController(UserSessionRepository sessionRepository) {
		 this.sessionRepository = sessionRepository;
	 }
	
	@RolesAllowed(UserSession.ROLE_USER)
	@GetMapping("/logout")
	public String logout(UserSession userSession) {
		sessionRepository.clear();
		return "redirect:/todos";
	}
	

}
