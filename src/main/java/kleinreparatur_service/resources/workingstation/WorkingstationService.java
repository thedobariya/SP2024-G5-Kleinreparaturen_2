package kleinreparatur_service.resources.workingstation;

import kleinreparatur_service.customer.Customer;
import kleinreparatur_service.resources.coworkers.Coworker;
import kleinreparatur_service.resources.workingstation.Workingstation;
import kleinreparatur_service.resources.workingstation.WorkingstationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WorkingstationService {

	private final WorkingstationRepository workingstationRepository;

	@Autowired
	public WorkingstationService(WorkingstationRepository workingstationRepository) {
		this.workingstationRepository = workingstationRepository;
	}

	public void setWorkingstationUnavailable(Long id) {
		workingstationRepository.findById(id).ifPresent(workingstation -> {
			workingstation.setAvailable(false);
			workingstationRepository.save(workingstation);
		});
	}

	public void setWorkingstationAvailable(Long id) {
		workingstationRepository.findById(id).ifPresent(workingstation -> {
			workingstation.setAvailable(true);
			workingstationRepository.save(workingstation);
		});
	}

	public List<Workingstation> findAvailableWorkingstations() {
		return workingstationRepository.findByAvailableTrue();
	}

	public List<Workingstation> findAllWorkingstations() {
		return workingstationRepository.findAll();
	}

	public Workingstation findById(Long id) {
		return workingstationRepository.findById(id).orElse(null);
	}

	public void deleteById(Long id) {
		workingstationRepository.deleteById(id);
	}

	public Workingstation updateWorkingstations(Workingstation workingstation) {
		return workingstationRepository.save(workingstation);
	}

	public Workingstation save(Workingstation workingstation) {
		if (workingstation.getPrice() < 0) {
			throw new IllegalArgumentException("Price cannot be negative");
		}
		return workingstationRepository.save(workingstation);
	}

	public void updatePrice(Long id, double price) {
		if (price < 0) {
			throw new IllegalArgumentException("Price cannot be negative");
		}
		Workingstation workingstation = findById(id);
		if (workingstation != null) {
			workingstation.setPrice(price);
			workingstationRepository.save(workingstation);
		} else {
			throw new IllegalArgumentException("Invalid workingstation ID: " + id);
		}
	}

	public List<Workingstation> findAllById(List<Long> workingstationIds) {
		return workingstationRepository.findAllById(workingstationIds);
	}
}
