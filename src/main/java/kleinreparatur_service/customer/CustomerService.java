package kleinreparatur_service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public Optional<Customer> findById(Long id) {
		return customerRepository.findById(id);
	}

	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	public Optional<Customer> findByFirstNameLastNameAndDateOfBirth(String firstName, String lastName, Date dateOfBirth) {
		return customerRepository.findByFirstNameAndLastNameAndDateOfBirth(firstName, lastName, dateOfBirth);
	}
}
