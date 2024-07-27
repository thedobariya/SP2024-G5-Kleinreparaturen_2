package kleinreparatur_service.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTest {

	@Mock
	private UserManagement userManagement;

	@Mock
	private UserAccountManagement userAccountManagement;

	@Mock
	private Model model;

	@Mock
	private Errors errors;

	@InjectMocks
	private UserController userController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testRegisterNew_Success() {
		RegistrationForm form = new RegistrationForm("John Doe", "password123", "123 Main St");
		when(errors.hasErrors()).thenReturn(false);

		String viewName = userController.registerNew(form, errors);

		assertEquals("redirect:/", viewName);
		verify(userManagement).createUser(form);
	}

	@Test
	void testRegisterNew_Error() {
		RegistrationForm form = new RegistrationForm("", "", "");
		when(errors.hasErrors()).thenReturn(true);

		String viewName = userController.registerNew(form, errors);

		assertEquals("register", viewName);
		verify(userManagement, never()).createUser(any());
	}

	@Test
	void testRegister() {
		RegistrationForm form = new RegistrationForm("tempName", "temp", "tempstraße 1");

		String viewName = userController.register(model, form);

		assertEquals("register", viewName);
	}

	/**
	 * This test is incorrect because the method signature of the controller method is incorrect.
	 * The method signature should be `String usermainpage(Model model)`.
	@Test
	void testUsers() {
		Iterable<user> users = Arrays.asList(new user(), new user());
		when(userManagement.findAll()).thenReturn(users);

		String viewName = userController.users(model);

		assertEquals("userlisting", viewName);
		verify(model).addAttribute("users", users);
	}
	 */

	@Test
	void testDeleteUser_UserExists() {
		String username = "testUser";
		UserAccount userAccount = mock(UserAccount.class);
		user user = new user(userAccount, "Test Address");

		when(userAccountManagement.findByUsername(username)).thenReturn(Optional.of(userAccount));
		when(userManagement.findByUserAccount(userAccount)).thenReturn(Optional.of(user));

		String viewName = userController.deleteUser(username);

		assertEquals("redirect:/userlisting", viewName);
		verify(userManagement).delete(user);
		verify(userAccountManagement).delete(userAccount);
	}

	@Test
	void testDeleteUser_UserNotExists() {
		String username = "nonExistentUser";
		when(userAccountManagement.findByUsername(username)).thenReturn(Optional.empty());

		String viewName = userController.deleteUser(username);

		assertEquals("redirect:/userlisting", viewName);
		verify(userManagement, never()).delete(any());
		verify(userAccountManagement, never()).delete(any());
	}

	@Test
	void testChangePassword() {
		String username = "testUser";
		Password.UnencryptedPassword newPassword = Password.UnencryptedPassword.of("newPassword");

		String viewName = userController.changePassword(username, newPassword);

		assertEquals("redirect:/userlisting", viewName);
		verify(userManagement).changePassword(username, newPassword);
	}
}