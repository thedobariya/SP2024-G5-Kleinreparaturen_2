package kickstart.welcome;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DepotController {

	@GetMapping("/depot")
	public String showDepot(Model model) {
		List<Item> newItems = new ArrayList<>();
		List<Item> finishedItems = new ArrayList<>();
		List<Item> donationItems = new ArrayList<>();

		// Creating items based on their day count
		List<Item> allItems = new ArrayList<>();
		allItems.add(new Item("Item 1", 50));
		allItems.add(new Item("Item 2", 15));
		allItems.add(new Item("Item 3", 25));
		allItems.add(new Item("Item 4", 35));
		allItems.add(new Item("Item 5", 45));
		allItems.add(new Item("Item 6", 5));
		allItems.add(new Item("Item 7", 15));
		allItems.add(new Item("Item 8", 25));
		allItems.add(new Item("Item 9", 35));
		allItems.add(new Item("Item 10", 45));

		// Categorize items
		for (Item item : allItems) {
			if (item.getDays() < 10) {
				newItems.add(item);
			} else if ((item.getDays() >= 10) && (item.getDays() < 30)) {
				finishedItems.add(item);
			} else {
				donationItems.add(item);
			}
		}

		// Calculate total number of items
		int totalItems = newItems.size() + finishedItems.size() + donationItems.size();

		model.addAttribute("newItems", newItems);
		model.addAttribute("finishedItems", finishedItems);
		model.addAttribute("donationItems", donationItems);

		// Add total number of items to the model
		model.addAttribute("totalItems", totalItems);

		return "depot";
	}




	private static class Item {
		private String name;
		private int days; // Change from double to int for days

		public Item(String name, int days) {
			this.name = name;
			this.days = days;
		}

		public String getName() {
			return name;
		}

		public int getDays() {
			return days;
		}
	}
}