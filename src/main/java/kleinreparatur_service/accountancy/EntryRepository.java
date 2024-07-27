package kleinreparatur_service.accountancy;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {
	List<Entry> findByDateBetween(LocalDate start, LocalDate end);
}