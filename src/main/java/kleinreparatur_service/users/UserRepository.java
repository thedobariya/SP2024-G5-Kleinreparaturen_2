package kleinreparatur_service.users;

import kleinreparatur_service.users.user.UserIdentifier;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

interface UserRepository extends JpaRepository<user, UserIdentifier> {

	@Override
	List<user> findAll();
	Optional<user> findByUserAccount(UserAccount userAccount);

}
