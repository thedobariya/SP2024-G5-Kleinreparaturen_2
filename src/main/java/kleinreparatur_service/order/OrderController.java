package kleinreparatur_service.order;

import jakarta.servlet.http.HttpSession;
import kleinreparatur_service.accountancy.Entry;
import kleinreparatur_service.accountancy.EntryService;
import kleinreparatur_service.customer.Customer;
import kleinreparatur_service.customer.CustomerService;
import kleinreparatur_service.item.Item;
import kleinreparatur_service.item.ItemService;
import kleinreparatur_service.resources.coworkers.Coworker;
import kleinreparatur_service.resources.coworkers.CoworkerService;
import kleinreparatur_service.resources.workingstation.Workingstation;
import kleinreparatur_service.resources.workingstation.WorkingstationService;
import kleinreparatur_service.servicecatalog.Service;
import kleinreparatur_service.servicecatalog.ServiceCatalog;
import kleinreparatur_service.servicecatalog.ServiceComponent;
import org.salespointframework.order.*;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Controller for handling order-related operations.
 * Requires authentication for all endpoints.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@SessionAttributes("cart")
public class OrderController {

	private final OrderManagement<CustomOrder> orderManagement;
	private final CustomerService customerService;
	private final CustomOrderService customOrderService;
	private final ItemService itemService;
	private final EntryService entryService;
	@Autowired
	private WorkingstationService workingstationService;

	@Autowired
	private ServiceComponent serviceComponent;

	@Autowired
	private ServiceCatalog serviceCatalog;

	@Autowired
	private CoworkerService coworkerService;

	public OrderController(OrderManagement<CustomOrder> orderManagement, CustomerService customerService,
			CustomOrderService customOrderService, ItemService itemService, EntryService entryService) {
		Assert.notNull(orderManagement, "OrderManagement must not be null!");
		Assert.notNull(customerService, "CustomerService must not be null!");
		Assert.notNull(customOrderService, "CustomOrderService must not be null!");
		Assert.notNull(itemService, "ItemService must not be null!");
		this.orderManagement = orderManagement;
		this.customerService = customerService;
		this.customOrderService = customOrderService;
		this.itemService = itemService;
		this.entryService = entryService;
	}

    /**
     * Initializes a new cart for the session.
     * @return A new Cart object.
     */
	@ModelAttribute("cart")
	Cart initializeCart() {
		return new Cart();
	}

    /**
     * Displays the cart page.
     * @param model The Model object to add attributes to.
     * @param cart The current cart in the session.
     * @return The name of the view to render.
     */
	@GetMapping("/cart")
	String basket(Model model, @ModelAttribute("cart") Cart cart) {
		if (cart.isEmpty())
			return "emptycart";

		model.addAttribute("cart", cart);
		model.addAttribute("availableWorkingstations", workingstationService.findAvailableWorkingstations());

		return "cart";
	}

    /**
     * Adds a service to the cart.
     * @param service The service to add.
     * @param number The quantity of the service.
     * @param cart The current cart in the session.
     * @param model The Model object to add attributes to.
     * @return The name of the view to render.
     */
	@PostMapping("/cart")
	String addService(@RequestParam("pid") Service service, @RequestParam("number") int number,
			@ModelAttribute Cart cart, Model model) {
		if (cart.isEmpty()) {
			int amount = 1;
			cart.addOrUpdateItem(service, Quantity.of(number));
			return "redirect:/items/newitem";
		} else {
			model.addAttribute("itemExist", true);
			model.addAttribute("service", service); // Ensure the service is available for rendering the page again
			return "serviceDetail";
		}
	}

    /**
     * Removes an item from the cart.
     * @param service The service to remove.
     * @param cart The current cart in the session.
     * @return A redirect to the cart page.
     */
	@PostMapping("/cart/remove")
	String removeItem(@RequestParam("pid") Service service, @ModelAttribute Cart cart) {
		// Find the CartItem for the given service
		Optional<CartItem> cartItemOpt = cart.stream()
				.filter(item -> item.getProduct().equals(service))
				.findFirst();

		// If the CartItem is found, remove it from the cart
		if (cartItemOpt.isPresent()) {
			cart.removeItem(cartItemOpt.get().getId());
		}
		return "redirect:/cart";
	}

    /**
     * Processes the checkout of the current cart.
     * @param cart The current cart in the session.
     * @param model The Model object to add attributes to.
     * @param userAccount The currently logged-in user account.
     * @param customerId The ID of the customer making the order.
     * @param workingstationId The ID of the workingstation for the order.
     * @param session The current HTTP session.
     * @return The name of the view to render.
     */
	@PostMapping("/checkout")
	String checkout(@ModelAttribute Cart cart, Model model, @LoggedIn Optional<UserAccount> userAccount,
			@RequestParam("customerId") Long customerId, @RequestParam("workingstationId") Long workingstationId,
			HttpSession session) {
		return userAccount.map(account -> {
			Optional<Customer> customerOpt = customerService.findById(customerId);
			if (customerOpt.isEmpty()) {
				model.addAttribute("message", "Customer not found.");
				return "cart";
			}

			Item item = (Item) session.getAttribute("newItem");
			if (item == null) {
				model.addAttribute("message", "No item found in session.");
				return "cart";
			}

			Optional<Item> itemOpt = itemService.findById(item.getId());
			if (itemOpt.isEmpty()) {
				model.addAttribute("message", "Item not found in database.");
				return "cart";
			}

			Optional<Workingstation> workingstationOpt = Optional
					.ofNullable(workingstationService.findById(workingstationId));
			if (workingstationOpt.isEmpty()) {
				model.addAttribute("message", "Workingstation not found.");
				return "cart";
			}

			try {
				var order = new CustomOrder(account.getId(), Cash.CASH);
				cart.addItemsTo(order);

				order.setCustomer(customerOpt.get());
				order.setItem(itemOpt.get());
				order.setWorkingstation(workingstationOpt.get());
				// Set the workingstation
				Workingstation workingstation = workingstationService.findById(workingstationId);
				order.setWorkingstation(workingstation);

				// Set the workingstation as unavailable
				workingstationService.setWorkingstationUnavailable(workingstationId);
				// Calculate the total price from the cart
				order.calculateTotalFromCart(cart);

				// Set initial order status
				order.setStatus(CustomOrder.OrderStatus.IN_WORKING);
				order.setOrderDate(LocalDate.now());

				// Process services
				for (OrderLine line : order.getOrderLines()) {
					Optional<Service> serviceOpt = serviceCatalog.findById(line.getProductIdentifier());
					if (serviceOpt.isPresent()) {
						Service service = serviceOpt.get();
						serviceComponent.setWorkingstationsUnavailable(service);
						serviceComponent.reduceMaterialQuantities(service);
					}
				}

				// Save the order before processing payment
				orderManagement.save(order);

				// Process payment
				orderManagement.payOrder(order);
				orderManagement.completeOrder(order);

				Entry entry = new Entry(order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName()
						+ " - " + order.getItem().getName(), order.getCustomTotal().getNumber().doubleValue());
				entryService.addEntry(entry);

				// Clear the cart and session
				cart.clear();
				session.removeAttribute("newItem");

				model.addAttribute("order", order);
				return "orderConfirmation";
			} catch (Exception e) {
				model.addAttribute("message", "Error processing order: " + e.getMessage());
				return "cart";
			}
		}).orElse("redirect:/login");
	}

    /**
     * Displays the list of completed orders.
     * @param model The Model object to add attributes to.
     * @return The name of the view to render.
     */
	@GetMapping("/orders")
	String orders(Model model) {
		List<CustomOrder> ordersCompleted = customOrderService.getCompletedOrders();
		List<Coworker> availableCoworkers = coworkerService.findAvailableCoworkers();

		model.addAttribute("ordersCompleted", ordersCompleted);
		model.addAttribute("customOrderService", customOrderService);
		model.addAttribute("availableCoworkers", availableCoworkers);
		return "orders";
	}

    /**
     * Assigns a coworker to an order.
     * @param orderId The ID of the order.
     * @param coworkerId The ID of the coworker to assign.
     * @return A redirect to the orders page.
     */
	@PostMapping("/orders/{orderId}/assignCoworker")
	public String assignCoworker(@PathVariable Order.OrderIdentifier orderId, @RequestParam Long coworkerId) {
		Optional<CustomOrder> orderOpt = orderManagement.get(orderId);
		Optional<Coworker> coworkerOpt = Optional.ofNullable(coworkerService.findById(coworkerId));

		if (orderOpt.isPresent() && coworkerOpt.isPresent()) {
			CustomOrder order = orderOpt.get();
			Coworker coworker = coworkerOpt.get();

			order.setAssignedCoworker(coworker);
			coworker.setAvailable(false);

			orderManagement.save(order);
			coworkerService.save(coworker);
		}
		return "redirect:/orders";
	}

    /**
     * Updates the status of an order.
     * @param orderId The ID of the order to update.
     * @param status The new status for the order.
     * @return A redirect to the orders page.
     */
	@PostMapping("/orders/{orderId}/status")
	public String updateOrderStatus(@PathVariable Order.OrderIdentifier orderId, @RequestParam String status) {
		Optional<CustomOrder> orderOpt = orderManagement.get(orderId);
		if (orderOpt.isEmpty()) {
			return "redirect:/orders";
		}

		CustomOrder order = orderOpt.get();

		// Update the order status based on the status value
		switch (status) {
			case "IN_WORKING":
				order.setStatus(CustomOrder.OrderStatus.IN_WORKING);
				break;
			case "COMPLETED":
				order.setStatus(CustomOrder.OrderStatus.COMPLETED);
				order.setCompletionDate(LocalDate.now());
				customOrderService.getRefundAmount(order);
				customOrderService.getStorageFee(order);
				if (order.getAssignedCoworker() != null) {
					Coworker coworker = order.getAssignedCoworker();
					coworker.setAvailable(true);
					coworkerService.save(coworker);
					order.setAssignedCoworker(null); // Optionally remove the assignment
				}
				if (order.getWorkingstation() != null) {
					workingstationService.setWorkingstationAvailable(order.getWorkingstation().getId());
				}
				break;
			case "ABGEHOLT":
				order.setStatus(CustomOrder.OrderStatus.ABGEHOLT);
				order.setPickUpDate(LocalDate.now());
				customOrderService.getRefundAmount(order);
				customOrderService.getStorageFee(order);
				if (order.getAssignedCoworker() != null) {
					Coworker coworker = order.getAssignedCoworker();
					coworker.setAvailable(true);
					coworkerService.save(coworker);
					order.setAssignedCoworker(null); // Optionally remove the assignment
				}
				if (order.getWorkingstation() != null) {
					workingstationService.setWorkingstationAvailable(order.getWorkingstation().getId());
				}
				break;
			default:
				return "redirect:/orders";
		}

		orderManagement.save(order);
		return "redirect:/orders";
	}
}