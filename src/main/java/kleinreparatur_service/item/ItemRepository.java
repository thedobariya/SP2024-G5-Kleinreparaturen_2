package kleinreparatur_service.item;

import org.jmolecules.ddd.annotation.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
	List<Item> findByIsOpenTrue();
	Optional<Item> findById(Long id);
}