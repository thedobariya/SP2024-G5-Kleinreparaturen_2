package kleinreparatur_service.resources.coworkers;

import kleinreparatur_service.resources.coworkers.Coworker;
import kleinreparatur_service.resources.coworkers.CoworkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoworkerService {

	private final CoworkerRepository coworkerRepository;

	@Autowired
	public CoworkerService(CoworkerRepository coworkerRepository) {
		this.coworkerRepository = coworkerRepository;
	}

	public List<Coworker> findAll() {
		return coworkerRepository.findAll();
	}

	public Coworker findById(Long id) {
		return coworkerRepository.findById(id).orElse(null);
	}

	public void deleteById(Long id) {
		coworkerRepository.deleteById(id);
	}

	public List<Coworker> findAllCoworkers() {
		return coworkerRepository.findAll();
	}

	public Coworker updateCoworkers(Coworker coworker) {
		return coworkerRepository.save(coworker);
	}

	public void updateHourlyRate(Long id, double hourlyRate) {
		if (hourlyRate < 0) {
			throw new IllegalArgumentException("Hourly rate cannot be negative");
		}
		Coworker coworker = findById(id);
		if (coworker != null) {
			coworker.setHourlyRate(hourlyRate);
			coworkerRepository.save(coworker);
		} else {
			throw new IllegalArgumentException("Invalid coworker ID: " + id);
		}
	}

	public List<Coworker> findAvailableCoworkers() {
		return coworkerRepository.findByAvailableTrue();
	}

	public Coworker save(Coworker coworker) {
		if (coworker.getHourlyRate() < 0) {
			throw new IllegalArgumentException("Hourly rate cannot be negative");
		}
		return coworkerRepository.save(coworker);
	}
}
