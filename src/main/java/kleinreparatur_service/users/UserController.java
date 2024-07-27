package kleinreparatur_service.users;

import jakarta.validation.Valid;

import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
class UserController {

	private final UserManagement userManagement;

	private UserAccountManagement userAccountManagement;
	UserController(UserManagement userManagement, UserAccountManagement userAccountManagement) {

		Assert.notNull(userManagement, "CustomerManagement must not be null!");

		this.userManagement = userManagement;
		this.userAccountManagement = userAccountManagement;
	}

	@PostMapping("/register")
	@PreAuthorize("hasRole('MANAGEMENTLEAD')")
	String registerNew(@Valid RegistrationForm form, Errors result) {

		if (result.hasErrors()) {
			return "register";
		}

		userManagement.createUser(form);

		return "redirect:/";
	}

	@GetMapping("/register")
	String register(Model model, RegistrationForm form) {
		return "register";
	}

	@GetMapping("/usermainpage")
	String register(Model model) {
		return "usermainpage";
	}

	@GetMapping("/userlisting")
	@PreAuthorize("hasRole('MANAGEMENTLEAD')")
	String users(Model model) {

		Iterable<user> users = userManagement.findAll();

		model.addAttribute("users", users);

		return "userlisting";
	}

	@PostMapping("/deleteUser")
	@PreAuthorize("hasRole('MANAGEMENTLEAD')")
	public String deleteUser(@RequestParam String username) {
		Optional<UserAccount> userAccountOptional = userAccountManagement.findByUsername(username);

		if (userAccountOptional.isPresent()) {
			UserAccount userAccount = userAccountOptional.get();

			// First, delete the associated user
			Optional<user> userOptional = userManagement.findByUserAccount(userAccount);
			if (userOptional.isPresent()) {
				user user = userOptional.get();
				userManagement.delete(user);
			}
			// Then, delete the UserAccount
			userAccountManagement.delete(userAccount);
		}

		return "redirect:/userlisting";
	}

	@PostMapping("/changePassword/{username}")
	@PreAuthorize("hasRole('MANAGEMENTLEAD')")
	public String changePassword(@PathVariable String username, @RequestParam("newpassword") Password.UnencryptedPassword newPassword) {
		userManagement.changePassword(username, newPassword);
		return "redirect:/userlisting";
	}

}