package kleinreparatur_service.item;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/items")
public class ItemController {

	private ItemService itemService;

	@Autowired
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping
	public String getAllItems(Model model, @RequestParam(required = false, defaultValue = "false") boolean openOnly) {
		List<Item> items = openOnly ? itemService.getAllOpenItems() : itemService.getAllItems();
		model.addAttribute("items", items);
		model.addAttribute("totalItems", items.size());
		model.addAttribute("openOnly", openOnly);
		model.addAttribute("item", new Item());
		return "depot";
	}

	@PostMapping("/prefill")
	public String prefillData() {
		itemService.prefillData();
		return "redirect:/items";
	}

	@GetMapping("/newitem")
	public String showNewItemForm(Model model) {
		model.addAttribute("item", new Item());
		return "newitem";
	}

	@PostMapping("/add")
	public String addItem(@ModelAttribute("item") Item item, HttpSession session) {
		Item savedItem = itemService.addItem(itemService.setID(), item.getName(), item.getDescription(), item.getCondition());
//		session.setAttribute("itemId", savedItem.getId()); // Store the item ID in the session
		session.setAttribute("newItem", savedItem); // Store the entire Item object in the session
		return "redirect:/cart";
	}

	@GetMapping("/receipt")
	public String showReceipt(Model model) {
		model.addAttribute("item", model);
		return "receipt";
	}
}