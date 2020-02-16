package todoapp.web.user;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import todoapp.core.user.application.UserJoinder;
import todoapp.core.user.application.UserPasswordVerifier;
import todoapp.core.user.domain.UserEntityNotFoundException;
import todoapp.core.user.domain.UserPasswordNotMatchedException;

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
	public String loginProcess(@Valid LoginCommand loginCommand, BindingResult bindingResult, Model model) {
		
		log.info("username: {}, password: {}", loginCommand.getUsername(), loginCommand.getPassword());
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("bindingResult", bindingResult);
			model.addAttribute("message", "사용자 입력 값이 올바르지 않습니다.");
			return "login";
		}
		
		try {
			verifier.verify(loginCommand.getUsername(), loginCommand.getPassword());
		} catch(UserEntityNotFoundException error) {
			joinder.join(loginCommand.getUsername(), loginCommand.getPassword());
		}
		
		return "redirect:/todos";
	}
	
	@ExceptionHandler(UserPasswordNotMatchedException.class)
	public String handleUserPasswordNotMatchedException(UserPasswordNotMatchedException error, Model model) {
		model.addAttribute("message", "사용자 정보가 올바르지 않습니다.");
		return "login";
	}
	
	static class LoginCommand {
		
		@Size(min = 4, max = 20)
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
