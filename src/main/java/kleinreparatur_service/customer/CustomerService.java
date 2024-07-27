package kleinreparatur_service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing customer-related operations.
 */
@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * Constructor for CustomerService.
	 * @param customerRepository The repository for customer data operations.
	 */
	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	/**
	 * Retrieves all customers from the database.
	 * @return A list of all customers.
	 */
	public List<Customer> findAllCustomers() {
		return customerRepository.findAll();
	}

	/**
	 * Finds a customer by their ID.
	 * @param id The ID of the customer to find.
	 * @return An Optional containing the customer if found, or empty if not found.
	 */
	public Optional<Customer> findById(Long id) {
		return customerRepository.findById(id);
	}

	/**
	 * Saves a new customer or updates an existing one.
	 * @param customer The customer to save or update.
	 * @return The saved or updated customer.
	 */
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	/**
	 * Finds a customer by their first name, last name, and date of birth.
	 * @param firstName The customer's first name.
	 * @param lastName The customer's last name.
	 * @param dateOfBirth The customer's date of birth.
	 * @return An Optional containing the customer if found, or empty if not found.
	 */
	public Optional<Customer> findByFirstNameLastNameAndDateOfBirth(String firstName, String lastName, Date dateOfBirth) {
		return customerRepository.findByFirstNameAndLastNameAndDateOfBirth(firstName, lastName, dateOfBirth);
	}

	/**
	 * Deletes a customer by their ID.
	 * @param id The ID of the customer to delete.
	 * @return true if the customer was found and deleted, false otherwise.
	 */
	public boolean deleteById(Long id) {
		if (customerRepository.existsById(id)) {
			customerRepository.deleteById(id);
			return true;
		}
		return false;
	}
}