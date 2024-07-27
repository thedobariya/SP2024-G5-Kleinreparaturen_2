package kleinreparatur_service.resources;

import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class ServiceInventoryController {
	private final UniqueInventory<UniqueInventoryItem> serviceInventory;

	ServiceInventoryController(UniqueInventory<UniqueInventoryItem> inventory) {
		this.serviceInventory = inventory;
	}

	@GetMapping("/stock")
//	@PreAuthorize("hasRole('MANAHEMENTLEAD')")
	String stock(Model model) {
		model.addAttribute("stock", serviceInventory.findAll());
		return "stock";
	}

}
