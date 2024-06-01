package kickstart.resources;

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
		allItems.add(new Item("Item 1", 7, 20.00)); // Will be categorized as new item
		allItems.add(new Item("Item 2", 8, 30.00)); // Will be categorized as finished item after one week
		allItems.add(new Item("Item 3", 25, 15.00)); // Will be categorized as finished item after one week
		allItems.add(new Item("Item 4", 3, 25.00)); // Will be categorized as new item
		allItems.add(new Item("Item 5", 45, 40.00)); // Will be categorized as donation item after three months
		allItems.add(new Item("Item 6", 5, 10.00)); // Will be categorized as new item
		allItems.add(new Item("Item 7", 15, 35.00)); // Will be categorized as finished item after one week
		allItems.add(new Item("Item 8", 1, 15.00)); // Will be categorized as new item
		allItems.add(new Item("Item 9", 100, 50.00)); // Will be categorized as donation item after three months
		allItems.add(new Item("Item 10", 140, 20.00)); // Will be categorized as donation item after three months

		// Categorize items
		for (Item item : allItems) {
			if (item.getDays() <= 7) {
				item.setStorageFee(1.50 * ((item.getDays() - 7) / 7)); // Calculate storage fee

				newItems.add(item);
			} else if ((item.getDays() > 7) && (item.getDays() < 90)) {
				// If item is between 8 and 89 days old, it is finished but not yet donation
				item.setStorageFee(1.50 * ((item.getDays() - 7) / 7)); // Calculate storage fee
				finishedItems.add(item);
			} else {
				item.setStorageFee(1.50 * ((item.getDays() - 7) / 7)); // Calculate storage fee

				// If item is 90 days old or more, it becomes a donation item
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
		private int days;
		private double storageFee;
		private double cost;

		public Item(String name, int days, double cost) {
			this.name = name;
			this.days = days;
			this.cost = cost;
		}

		public String getName() {
			return name;
		}

		public int getDays() {
			return days;
		}

		public double getStorageFee() {
			return storageFee;
		}

		public void setStorageFee(double storageFee) {
			this.storageFee = storageFee;
		}

		public double getCost() {
			return cost;
		}

		// Method to calculate the final cost including storage fees
		public double calculateFinalCost() {
			return cost + storageFee;
		}
	}
}
