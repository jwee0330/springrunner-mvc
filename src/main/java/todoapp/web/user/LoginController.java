package todoapp.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public void loginForm() {
	}
	
	@PostMapping("/login")
	public String login() {
		return "login";
	}

}
