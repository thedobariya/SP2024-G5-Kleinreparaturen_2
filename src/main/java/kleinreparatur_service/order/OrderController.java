package kickstart.order;

import kickstart.customer.Customer;
import kickstart.customer.CustomerService;
import kickstart.item.Item;
import kickstart.item.ItemService;
import kickstart.servicecatalog.Service;
import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.salespointframework.useraccount.QUserAccount.userAccount;

@Controller
//@PreAuthorize("isAuthenticated()")
@SessionAttributes("cart")
public class OrderController {

    private final OrderManagement<CustomOrder> orderManagement;
    private final CustomerService customerService;
    private final ItemService itemService;


	public OrderController(OrderManagement<CustomOrder> orderManagement, CustomerService customerService, ItemService itemService) {
		Assert.notNull(orderManagement, "OrderManagement must not be null!");
        Assert.notNull(customerService, "CustomerService must not be null!");
		Assert.notNull(itemService, "ItemService must not be null!");
        this.orderManagement = orderManagement;
        this.customerService = customerService;
		this.itemService = itemService;
	}

	@ModelAttribute("cart")
	Cart initializeCart() {
		return new Cart();
	}

	@PostMapping("/cart")
	String addService(@RequestParam("pid") Service service, @RequestParam("number") int number, @ModelAttribute Cart cart) {

		// (｡◕‿◕｡)
		// Das Inputfeld im View ist eigentlich begrenzt, allerdings sollte man immer auch serverseitig validieren
		int amount = number <= 0 || number > 5 ? 1 : number;

		// (｡◕‿◕｡)
		// Wir fügen dem Warenkorb die Disc in entsprechender Anzahl hinzu.
//		cart.addOrUpdateItem(service, Quantity.of(amount));

		// (｡◕‿◕｡)
		// Je nachdem ob disc eine DVD oder eine Bluray ist, leiten wir auf die richtige Seite weiter

		/*switch (service.getType()) {
			case DVD:
				return "redirect:dvds";
			case BLURAY:
			default:
				return "redirect:blurays";
		}*/
		return null;
	}

	@GetMapping("/cart")
	String basket() {
		return "cart";
	}

    @PostMapping("/checkout")
    String buy(@RequestParam("customerId") Long customerId,@RequestParam("itemId") Long itemId, @ModelAttribute Cart cart, @LoggedIn Optional<UserAccount> userAccount, Model model) {
        return userAccount.map(account -> {
            Optional<Customer> customerOpt = customerService.findById(customerId);
            if (!customerOpt.isPresent()) {
                model.addAttribute("message", "Customer not found.");
                return "cart";
            }

			Optional<Item> itemOpt = ItemService.findById(itemId);
			if (!itemOpt.isPresent()) {
				model.addAttribute("message", "Item not found.");
				return "cart";
			}

			CustomOrder order = new CustomOrder(/*userAccount.getId(), Cash.CASH*/);
            order.setCustomer(customerOpt.get());

            cart.addItemsTo(order);
            orderManagement.payOrder(order);
            orderManagement.completeOrder(order);
            cart.clear();

            model.addAttribute("message", "Order placed successfully with Order ID: " + order.getId());
            return "orderSuccess";
        }).orElse("redirect:/cart");
    }
}
