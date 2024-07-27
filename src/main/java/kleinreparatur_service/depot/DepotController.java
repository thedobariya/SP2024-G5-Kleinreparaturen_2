package kleinreparatur_service.depot;

import kleinreparatur_service.customer.CustomerService;
import kleinreparatur_service.materials.Material;
import kleinreparatur_service.materials.MaterialService;
import kleinreparatur_service.order.CustomOrder;
import kleinreparatur_service.order.CustomOrderService;
import kleinreparatur_service.resources.workingstation.Workingstation;
import kleinreparatur_service.resources.coworkers.CoworkerService;
import kleinreparatur_service.resources.coworkers.Coworker;
import kleinreparatur_service.resources.workingstation.WorkingstationService;
import kleinreparatur_service.item.Item;
import kleinreparatur_service.item.ItemService;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/depot")
public class DepotController {

	private final WorkingstationService workingstationService;
	private final CoworkerService coworkerService;
	private final MaterialService materialService;
	private final ItemService itemService;
	private final CustomOrderService customOrderService;
	private final OrderManagement<CustomOrder> orderManagement;


	@Autowired
	public DepotController(WorkingstationService workingstationService, CoworkerService coworkerService, MaterialService materialService, ItemService itemService, CustomOrderService customorderService, OrderManagement<CustomOrder> orderManagement) {
		this.workingstationService = workingstationService;
		this.coworkerService = coworkerService;
		this.materialService = materialService;
		this.itemService = itemService;
		this.customOrderService = customorderService;
		this.orderManagement = orderManagement;
	}

	/**
	 * Displays the main depot page.
	 */
	@GetMapping()
	public String depot() {
		return "depot";
	}

	/**
	 * Displays a list of all workingstations.
	 */
	@GetMapping("/workingstations")
	public String showWorkingstationsList(Model model) {
		List<Workingstation> workingstations = workingstationService.findAllWorkingstations();
		model.addAttribute("workingstations", workingstations);
		return "depot";
	}

	/**
	 * Displays a list of all coworkers.
	 */
	@GetMapping("/coworkers")
	public String showCoworkersList(Model model) {
		List<Coworker> coworkers = coworkerService.findAllCoworkers();
		model.addAttribute("coworkers", coworkers);
		return "depot";
	}

	/**
	 * Displays a list of all materials.
	 */
	@GetMapping("/materials")
	public String showMaterialsList(Model model) {
		List<Material> materials = materialService.findAllMaterials();
		model.addAttribute("materials", materials);
		return "depot";
	}

	/**
	 * Displays a list of completed orders.
	 */
	@GetMapping("/orders")
	String orders(Model model) {
		List<CustomOrder> ordersCompleted = customOrderService.getCompletedOrders();

		model.addAttribute("ordersCompleted", ordersCompleted);
		model.addAttribute("customOrderService", customOrderService);

		return "depot";
	}

	/**
	 * Displays a list of all items.
	 */
	@GetMapping("/items")
	public String findAllItems(Model model) {
		List<Item> items = itemService.findAllItems();
		model.addAttribute("items", items);
		return "depot";
	}
}