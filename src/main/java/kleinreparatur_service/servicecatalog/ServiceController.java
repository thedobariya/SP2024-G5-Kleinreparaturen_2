package kleinreparatur_service.servicecatalog;

import kleinreparatur_service.materials.*;
import kleinreparatur_service.resources.workingstation.Workingstation;
import kleinreparatur_service.resources.workingstation.WorkingstationService;
import org.javamoney.moneta.Money;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing services in the kleinreparatur service system.
 * Handles CRUD operations for services and their related inventory items.
 */
@Controller
@RequestMapping("/services")
@PreAuthorize("isAuthenticated()")
public class ServiceController {
	private final UniqueInventory<UniqueInventoryItem> serviceInventory;
	private final ServiceComponent serviceComponent;
	private final WorkingstationService workingstationService;
	private final MaterialService materialService;
	
	@Autowired
	private WorkingstationService workingStationService;

    /**
     * Constructs a new ServiceController with necessary dependencies.
     *
     * @param serviceComponent The service component for business logic
     * @param serviceInventory The inventory for managing service items
     * @param workingstationService The service for managing workingstations
     * @param materialService The service for managing materials
     */
	@Autowired
	public ServiceController(ServiceComponent serviceComponent,UniqueInventory<UniqueInventoryItem> serviceInventory,  WorkingstationService workingstationService,
							 MaterialService materialService) {
		this.serviceComponent = serviceComponent;
		this.serviceInventory = serviceInventory;
		this.workingstationService = workingstationService;
		this.materialService = materialService;
	}

    /**
     * Displays the list of all services.
     *
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
	@GetMapping
	public String showServiceList(Model model) {
		List<Service> services = serviceComponent.findAllServices();
		model.addAttribute("services", services);
		List<Workingstation> workingstations = workingStationService.findAllWorkingstations();
		model.addAttribute("workingstations", workingstations);
		List<Material> materials = materialService.findAllMaterials();
		model.addAttribute("materials", materials);
		model.addAttribute("service", new Service());
		return "services";
	}

    /**
     * Displays the form for creating a new service.
     *
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("service", new Service());
		return "services";
	}

    /**
     * Handles the creation of a new service.
     *
     * @param name The name of the service
     * @param type The type of the service
     * @param description The description of the service
     * @param price The price of the service
     * @param workload The workload of the service
     * @param workingstationIds The IDs of the workingstations associated with the service
     * @param materialIds The IDs of the materials used in the service
     * @param quantities The quantities of materials used
     * @param model The model to add attributes to
     * @return The name of the view to render or redirect URL
     */
	@PostMapping("/create")
	public String createService(@RequestParam("name") String name,
								@RequestParam("ServiceType") Service.ServiceType type,
								@RequestParam("description") String description,
								@RequestParam("price") double price,
								@RequestParam("workload") double workload,
								@RequestParam("workingstation") List<Long> workingstationIds,
								@RequestParam("materials") List<Long> materialIds,
								@RequestParam Map<String, String> quantities,
								Model model) {
		try {
			Money moneyPrice = convertToMoney(price);
			List<Workingstation> selectedWorkingstations = workingStationService.findAllById(workingstationIds);
			List<Material> selectedMaterials = materialService.findAllById(materialIds);

			Service service = new Service(name, description, moneyPrice, type, workload, selectedWorkingstations, selectedMaterials);
			service.setWorkingstations(selectedWorkingstations);

			// Set material quantities
			for (Material material : selectedMaterials) {
				String quantityKey = "quantities[" + material.getId() + "]";
				if (quantities.containsKey(quantityKey)) {
					int quantity = Integer.parseInt(quantities.get(quantityKey));
					service.setMaterialQuantity(material, quantity);
				}
			}

			serviceComponent.saveService(service);

			// Create inventory item for the new service
			UniqueInventoryItem inventoryItem = new UniqueInventoryItem(service, Quantity.of(10));
			serviceInventory.save(inventoryItem);
			return "redirect:/services";
		} catch (Exception e) {
			model.addAttribute("error", "Fehler beim Erstellen des Service: " + e.getMessage());
			return "services";
		}
	}

    /**
     * Displays the form for editing an existing service.
     *
     * @param id The ID of the service to edit
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") Long id, Model model) {
		Service service = serviceComponent.findById(id);
		model.addAttribute("service", service);
		return "redirect:/service";
	}

    /**
     * Handles the update of an existing service.
     *
     * @param id The ID of the service to update
     * @param name The updated name of the service
     * @param serviceType The updated type of the service
     * @param description The updated description of the service
     * @param price The updated price of the service
     * @param workload The updated workload of the service
     * @param workingstations The updated workingstations for the service
     * @param materials The updated materials for the service
     * @param model The model to add attributes to
     * @return The redirect URL after update
     */
	@PostMapping("/edit/{id}")
	public String updateService(@RequestParam("id") long id,
								@RequestParam("name") String name,
								@RequestParam("type") Service.ServiceType serviceType,
								@RequestParam("description") String description,
								@RequestParam("price") Money price,
								@RequestParam("workload") int workload,
								@RequestParam("workingstations") Long workingstations,
								@RequestParam("materials") Long materials,
								Model model) {
		Service service = serviceComponent.findById(id);
		service.setName(name);
		service.setDescription(description);
		service.setPrice(price);
		service.setWorkload(workload);
		serviceComponent.updateService(service);
		return "redirect:/services";
	}

    /**
     * Handles the deletion of a service.
     *
     * @param id The ID of the service to delete
     * @param model The model to add attributes to
     * @return The redirect URL after deletion
     */
	@GetMapping("/delete/{id}")
	public String deleteService(@PathVariable("id") Long id, Model model) {
		serviceComponent.deleteService(id);
		return "redirect:/services";
	}

    /**
     * Converts a double amount to a Money object.
     *
     * @param amount The amount to convert
     * @return A Money object representing the amount in EUR
     */
	public Money convertToMoney(double amount) {
		return Money.of(amount, Monetary.getCurrency("EUR"));
	}
}
