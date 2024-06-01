package kleinreparatur_service.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, Date dateOfBirth);
}
