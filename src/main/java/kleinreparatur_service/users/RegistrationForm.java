package kleinreparatur_service.users;

import jakarta.validation.constraints.NotEmpty;

import org.springframework.validation.Errors;

class RegistrationForm {
	
	private final @NotEmpty String name, password, address;

	public RegistrationForm(String name, String password, String address) {

		this.name = name;
		this.password = password;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getAddress() {
		return address;
	}

	public void validate(Errors errors) {
		// Complex validation goes here
	}
}
