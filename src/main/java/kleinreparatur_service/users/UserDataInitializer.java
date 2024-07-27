package kleinreparatur_service.users;

import java.util.List;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Initializes default user accounts and customers. The following are created:
 * <ul>
 * <li>An admin user named "ManagementLead".</li>
 * <li>The workers "peter", "hans", "kathi", "deez" backed by user accounts with the same
 * name.</li>
 * </ul>
 *
 */

@Component
@Order(10)
class UserDataInitializer implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(UserDataInitializer.class);

	private final UserAccountManagement userAccountManagement;

	private final UserManagement userManagement;

	/**
	 * Creates a new {@link UserDataInitializer} with the given {@link UserAccountManagement} and
	 * {@link UserRepository}.
	 *
	 * @param userAccountManagement must not be {@literal null}.
	 * @param userManagement must not be {@literal null}.
	 */

	UserDataInitializer(UserAccountManagement userAccountManagement, UserManagement userManagement) {

		Assert.notNull(userAccountManagement, "UserAccountManagement must not be null!");
		Assert.notNull(userManagement, "UserRepository must not be null!");

		this.userAccountManagement = userAccountManagement;
		this.userManagement = userManagement;
	}

	@Override
	public void initialize() {

		// Skip creation if database was already populated
		if (userAccountManagement.findByUsername("ManagementLead").isPresent()) {
			return;
		}

		LOG.info("Creating default users.");

		userAccountManagement.create("ManagementLead", UnencryptedPassword.of("123"), Role.of("MANAGEMENTLEAD"));
		userAccountManagement.create("prit", UnencryptedPassword.of("123"), Role.of("WORKER"));

		var password = "123";

		List.of(//
				new RegistrationForm("SCHUSTEREI", password, "Standort HoMe/SA"),
				new RegistrationForm("NAEHSERVICE", password, "Standort HoMe/SA"),
				new RegistrationForm("SCHLUESSELDIENST", password, "Standort HoMe/SA"),
				new RegistrationForm("TEXTILSERVICE", password, "Standort HoMe/SA"),
				new RegistrationForm("ELEKTROWERKSTATT", password, "Standort HoMe/SA"),
				new RegistrationForm("METALLWERKSTATT", password, "Standort HoMe/SA")
		).forEach(userManagement::createUser);
	}
}
