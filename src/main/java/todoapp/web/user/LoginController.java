package todoapp.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import todoapp.core.user.application.UserJoinder;
import todoapp.core.user.application.UserPasswordVerifier;
import todoapp.core.user.domain.UserEntityNotFoundException;

@Controller
public class LoginController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private UserJoinder joinder;
	private UserPasswordVerifier verifier;
	
	public LoginController(UserJoinder joinder, UserPasswordVerifier verifier) {
		this.joinder = joinder;
		this.verifier = verifier;
	}
	
	@GetMapping("/login")
	public void loginForm() {
	}
	
	@PostMapping("/login")
	public String loginProcess(LoginCommand loginCommand) {
		
		log.info("username: {}, password: {}", loginCommand.getUsername(), loginCommand.getPassword());
		
		try {
			verifier.verify(loginCommand.getUsername(), loginCommand.getPassword());
		} catch(UserEntityNotFoundException error) {
			joinder.join(loginCommand.getUsername(), loginCommand.getPassword());
		}
		
		return "redirect:/todos";
	}
	
	static class LoginCommand {
		
		private String username;
		private String password;
		
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	}

}
