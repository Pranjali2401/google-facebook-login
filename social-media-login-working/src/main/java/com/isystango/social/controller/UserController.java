package com.isystango.social.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isystango.social.model.User;
import com.isystango.social.service.EmailSenderService;
import com.isystango.social.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailSenderService emailSender;

	@GetMapping("/")
	public String mainPage() {
		System.out.println("Home page");
		return "login";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/registration")
	public String showRegistration() {
		return "registration";
	}

	@PostMapping("/registration")
	public String setUser(@ModelAttribute User user) {
		emailSender.sendEmail(user.getEmail(), "Hello this is test mail", "Testing");
		userService.setUser(user);
		return "login";

	}

	@PostMapping("/login")
	public String validUser(@ModelAttribute User user, Model model) {
		User validUser = userService.validateUser(user.getEmail(), user.getPassword());
		System.out.println(validUser);
		model.addAttribute("name", validUser.getUsername());
		model.addAttribute("email", validUser.getEmail());
		return validUser != null ? "home" : "login";

	}

	@GetMapping("/signin/google")
	public String validClient(Principal principal, Model model) {
		System.out.println("in google login");
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();

		DefaultOidcUser logUser = (DefaultOidcUser) loggedInUser.getPrincipal();

		String uName = logUser.getAttribute("name");
		String uEmail = logUser.getAttribute("email");
		String nonce = logUser.getAttribute("nonce");

//		String token = logUser.getAttribute("at_hash");
//		String azp = logUser.getAttribute("azp");

		model.addAttribute("name", uName);
		model.addAttribute("email", uEmail);

		User user = new User();
		user.setEmail(uEmail);
		user.setUsername(uName);
		user.setAuthProvier("GOOGLE");
		user.setPassword(nonce);

		userService.setUser(user);

		return "home";

	}

	@GetMapping("/signin/facebook")
	public String validFacebookClient(Principal principal, Model model) {
		System.out.println("in facebook login");

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		DefaultOAuth2User logUser = (DefaultOAuth2User) loggedInUser.getPrincipal();

		String uName = logUser.getAttribute("name");
		String uEmail = logUser.getAttribute("email");
		String uId = logUser.getAttribute("id");

		model.addAttribute("name", uName);
		model.addAttribute("email", uEmail);

		User user = new User();
		user.setEmail(uEmail);
		user.setUsername(uName);
		user.setAuthProvier("FACEBOOK");
		user.setPassword(uId);

		userService.setUser(user);
		System.out.println(uName + " : " + uEmail + " : " + uId);

		return "home";
	}

}
