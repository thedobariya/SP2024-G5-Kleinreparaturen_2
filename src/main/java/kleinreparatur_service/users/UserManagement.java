package kleinreparatur_service.users;

import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of business logic related to {@link user}s.
 */

@Service
@Transactional
public class UserManagement {

	public static final Role WORKER_ROLE = Role.of("WORKER");

	private final UserRepository users;

	private final UserAccountManagement userAccounts;


	/**
	 * Creates a new {@link UserManagement} with the given {@link UserRepository} and
	 * {@link UserAccountManagement}.
	 *
	 * @param users must not be {@literal null}.
	 * @param userAccounts must not be {@literal null}.
	 */
	UserManagement(UserRepository users, UserAccountManagement userAccounts) {

		Assert.notNull(users, "UserRepository must not be null!");
		Assert.notNull(userAccounts, "UserAccountManagement must not be null!");

		this.users = users;
		this.userAccounts = userAccounts;

	}

	/**
	 * Creates a new {@link user} using the information given in the {@link RegistrationForm}.
	 *
	 * @param form must not be {@literal null}.
	 * @return the new {@link user} instance.
	 */
	public user createUser(RegistrationForm form) {

		Assert.notNull(form, "Registration form must not be null!");

		var password = UnencryptedPassword.of(form.getPassword());
		var userAccount = userAccounts.create(form.getName(), password, WORKER_ROLE);

		return users.save(new user(userAccount, form.getAddress()));
	}

	/**
	 * Returns all {@link user}s currently available in the system.
	 *
	 * @return all {@link user} entities.
	 */
	public List<user> findAll() {
		return users.findAll();
	}

	public void deleteuserAccount(UserAccount userAccount) {
		userAccounts.delete(userAccount);
	}

	public void changePassword(String username, Password.UnencryptedPassword newPassword) {
		Optional<UserAccount> userAccountOptional = userAccounts.findByUsername(username);
		if (userAccountOptional.isPresent()) {
			UserAccount userAccount = userAccountOptional.get();
			userAccounts.changePassword(userAccount, newPassword);
		}
	}

	public void save(UserAccount userAccount) {
		userAccounts.save(userAccount);
	}

	public Optional<UserAccount> findByUsername(String username) {
		return userAccounts.findByUsername(username);
	}

	public Optional<user> findByUserAccount(UserAccount userAccount) {
		return users.findByUserAccount(userAccount);
	}
	public void delete(user user) {
		users.delete(user);
	}
}
