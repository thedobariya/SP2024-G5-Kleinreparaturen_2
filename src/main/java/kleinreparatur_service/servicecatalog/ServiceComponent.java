package kleinreparatur_service.servicecatalog;

import kleinreparatur_service.materials.Material;
import kleinreparatur_service.materials.MaterialService;
import kleinreparatur_service.resources.workingstation.Workingstation;
import kleinreparatur_service.resources.workingstation.WorkingstationService;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Component for managing service-related operations.
 */
@Component
public class ServiceComponent {
	private final ServiceRepository serviceRepository;
	private final WorkingstationService workingstationService;
	private final MaterialService materialService;

	/**
	 * Constructor for ServiceComponent.
	 * @param serviceRepository The repository for service data operations.
	 * @param workingstationService The service for workingstation operations.
	 * @param materialService The service for material operations.
	 */
	@Autowired
	public ServiceComponent(ServiceRepository serviceRepository,
							WorkingstationService workingstationService,
							MaterialService materialService) {
		this.serviceRepository = serviceRepository;
		this.workingstationService = workingstationService;
		this.materialService = materialService;
	}

	/**
	 * Retrieves all services.
	 * @return A list of all services.
	 */
	public List<Service> findAllServices() {
		return serviceRepository.findAll();
	}

	/**
	 * Saves a new service.
	 * @param service The service to save.
	 * @return The saved service.
	 */
	public Service saveService(Service service) {
		return serviceRepository.save(service);
	}

	/**
	 * Updates an existing service.
	 * @param service The service to update.
	 * @return The updated service.
	 */
	public Service updateService(Service service) {
		return serviceRepository.save(service);
	}

	/**
	 * Deletes a service by its ID.
	 * @param id The ID of the service to delete.
	 */
	public void deleteService(Long id) {
		serviceRepository.deleteById(id);
	}

	/**
	 * Finds a service by its ID.
	 * @param id The ID of the service to find.
	 * @return The found service.
	 * @throws IllegalArgumentException if no service is found with the given ID.
	 */
	public Service findById(Long id) {
		return serviceRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Invalid Service ID: " + id));
	}

	/**
	 * Sets all workingstations associated with a service as unavailable.
	 * @param service The service whose workingstations should be set as unavailable.
	 */
	public void setWorkingstationsUnavailable(Service service) {
		for (Workingstation workingstation : service.getWorkingstations()) {
			workingstationService.setWorkingstationUnavailable(workingstation.getId());
		}
	}

	/**
	 * Reduces the stock of materials associated with a service.
	 * @param service The service whose materials should have their stock reduced.
	 */
	public void reduceMaterialQuantities(Service service) {
		for (Map.Entry<Material, Integer> entry : service.getMaterialQuantities().entrySet()) {
			Material material = entry.getKey();
			int quantity = entry.getValue();
			materialService.reduceMaterialStock(material.getId(), quantity);
		}
	}

	/**
	 * Updates the price of a service.
	 * @param id The ID of the service to update.
	 * @param price The new price for the service.
	 * @return The updated service.
	 * @throws IllegalArgumentException if no service is found with the given ID.
	 */
	public Service updateServicePrice(Long id, Money price) {
		Service service = serviceRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Invalid Service ID: " + id));
		service.setPrice(price);
		return serviceRepository.save(service);
	}

	/**
	 * Updates the workload of a service.
	 * @param id The ID of the service to update.
	 * @param workload The new workload for the service.
	 * @return The updated service.
	 * @throws IllegalArgumentException if no service is found with the given ID.
	 */
	public Service updateServiceWorkload(Long id, int workload) {
		Service service = serviceRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Invalid Service ID: " + id));
		service.setWorkload(workload);
		return serviceRepository.save(service);
	}
}