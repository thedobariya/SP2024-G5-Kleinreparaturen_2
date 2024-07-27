package kleinreparatur_service.servicecatalog;

import kleinreparatur_service.servicecatalog.Service.ServiceType;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.BusinessTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This Controller is for showing the different services that are available at the moment.
 */
@Controller
@RequestMapping("/catalog")
public class ServiceCatalogController {

	private static final Quantity NONE = Quantity.of(0);

	private final ServiceCatalog catalog;
	private final UniqueInventory<UniqueInventoryItem> inventory;
	private final BusinessTime businessTime;

	@GetMapping()
	public String showCatalog(Model model) {
		model.addAttribute("catalog", new Service());
		return "catalog";
	}

	ServiceCatalogController(ServiceCatalog serviceCatalog, UniqueInventory<UniqueInventoryItem> inventory,
			BusinessTime businessTime) {

		this.catalog = serviceCatalog;
		this.inventory = inventory;
		this.businessTime = businessTime;
	}
	
	@GetMapping("/flickschusterei")
	String flickschustereiCatalog(Model model) {
		model.addAttribute("catalog", catalog.findByType(ServiceType.FLICKSCHUSTEREI));
		model.addAttribute("title", "catalog.flickschusterei.title");
		return "serviceCatalog";
	}

	@GetMapping("/naehservice")
	String naehserviceCatalog(Model model) {
		model.addAttribute("catalog", catalog.findByType(ServiceType.NAEHSERVICE));
		model.addAttribute("title", "catalog.naehservice.title");
		return "serviceCatalog";
	}

	@GetMapping("/metallarbeiten")
	String metallarbeitenCatalog(Model model) {
		model.addAttribute("catalog", catalog.findByType(ServiceType.METALLARBEITEN));
		model.addAttribute("title", "catalog.metallarbeiten.title");
		return "serviceCatalog";
	}

	@GetMapping("/textilservice")
	String textilserviceCatalog(Model model) {
		model.addAttribute("catalog", catalog.findByType(ServiceType.TEXTILSERVICE));
		model.addAttribute("title", "catalog.textilservice.title");
		return "serviceCatalog";
	}

	@GetMapping("/elektrowerkstatt")
	String elektrowerkstattCatalog(Model model) {
		model.addAttribute("catalog", catalog.findByType(ServiceType.ELEKTROWERKSTATT));
		model.addAttribute("title", "catalog.elektrowerkstatt.title");
		return "serviceCatalog";
	}

	@GetMapping("/schluesseldienst")
	String schluesseldienstCatalog(Model model) {
		model.addAttribute("catalog", catalog.findByType(ServiceType.SCHLUESSELDIENST));
		model.addAttribute("title", "catalog.schluesseldienst.title");
		return "serviceCatalog";
	}

	@GetMapping("/service/{service}")
	String detail(@PathVariable Service service, Model model) {

		var quantity = inventory.findByProductIdentifier(service.getId()) //
			.map(InventoryItem::getQuantity) //
			.orElse(NONE);

		model.addAttribute("service", service);
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(NONE));

		return "serviceDetail";
	}
}
