package kleinreparatur_service.servicecatalog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
	@Query("SELECT s FROM Service s LEFT JOIN FETCH s.workingstations LEFT JOIN FETCH s.materials")
	List<Service> findAllWithWorkingstationsAndMaterials();
}