package kleinreparatur_service.resources.workingstation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkingstationRepository extends JpaRepository<Workingstation, Long> {
	Optional<Workingstation> findByNameAndPrice(String Name, double Price);
	List<Workingstation> findByAvailableTrue();


}

