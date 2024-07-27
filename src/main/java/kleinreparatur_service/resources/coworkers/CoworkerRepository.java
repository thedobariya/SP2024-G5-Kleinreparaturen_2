package kleinreparatur_service.resources.coworkers;

import kleinreparatur_service.resources.coworkers.Coworker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoworkerRepository extends JpaRepository<Coworker, Long> {
	List<Coworker> findByAvailableTrue();
}