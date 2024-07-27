package kleinreparatur_service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Controller for handling customer-related HTTP requests.
 */
@Controller
@RequestMapping("/customers")
@PreAuthorize("isAuthenticated()")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

    /**
     * Displays the form for creating a new customer.
     */
    @GetMapping()
    public String createCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer";
    }

    /**
     * Handles the creation of a new customer.
     */
	@PostMapping("/create")
	public String saveCustomer(@RequestParam("firstName") String firstName,
							   @RequestParam("lastName") String lastName,
							   @RequestParam("dateOfBirth") String dateOfBirthStr,
							   Model model) {
		try {
			Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirthStr);
			Optional<Customer> customerOpt = customerService.findByFirstNameLastNameAndDateOfBirth(firstName, lastName, dateOfBirth);
			if (customerOpt.isPresent()) {
				model.addAttribute("message", "Kunde mit der ID " + customerOpt.get().getId() + " existiert bereits.");
			}else {
				Customer customer = new Customer(firstName, lastName, dateOfBirth);
				Customer savedCustomer = customerService.save(customer);
				model.addAttribute("message", "Kunde mit ID " + savedCustomer.getId() + " erfolgreich erstellt.");
			}
		} catch (Exception e) {
			model.addAttribute("message", "Fehler beim Erstellen des Kunden " + e.getMessage() + " aufgetreten.");
		}
		return "customer";
	}

    /**
     * Searches for a customer based on first name, last name, and date of birth.
     */
	@PostMapping("/search")
	public String searchCustomer(@RequestParam("firstName") String firstName,
								 @RequestParam("lastName") String lastName,
								 @RequestParam("dateOfBirth") String dateOfBirthStr,
								 Model model) {
		try {
			Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirthStr);
			Optional<Customer> customerOpt = customerService.findByFirstNameLastNameAndDateOfBirth(firstName, lastName, dateOfBirth);
			if (customerOpt.isPresent()) {
				model.addAttribute("message", "Kunde mit ID gefunden: " + customerOpt.get().getId());
			} else {
				model.addAttribute("message", "Kunde mit dieser ID existiert nicht.");
			}
		} catch (Exception e) {
			model.addAttribute("message", "Fehler beim Suchen nach: " + e.getMessage() + " aufgetreten.");
		}
		return "customer";
	}

    /**
     * Searches for a customer by ID.
     */
	@PostMapping("/searchCustomerWithId")
	public String searchCustomerById(@RequestParam("id") Long id, Model model) {
		// Search the customer by ID
		Optional<Customer> customerOpt = customerService.findById(id);

		if (customerOpt.isPresent()) {
			// Add the customer to the model
			model.addAttribute("customer", customerOpt.get());

			// Format the date of birth
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			String formattedDateOfBirth = formatter.format(customerOpt.get().getDateOfBirth());

			// Show details about the customer by name and date of birth in each line
			String message = "Kunde mit ID " + customerOpt.get().getId() + " gefunden. <br>" +
				"Vorname: " + customerOpt.get().getFirstName() + "<br>" +
				"Nachname: " + customerOpt.get().getLastName() + "<br>" +
				"Geburtsdatum: " + formattedDateOfBirth;
			model.addAttribute("message", message);
		} else {
			model.addAttribute("message", "Kunde existiert nicht.");
		}
		// Return the customer view
		return "customer";
	}

    /**
     * Deletes a customer by ID.
     */
	@PostMapping("/delete")
	public String deleteCustomer(@RequestParam("id") Long id, @RequestParam("location") int location, Model model, RedirectAttributes redirectAttributes) {
		try {
			boolean isDeleted = customerService.deleteById(id);
			if (isDeleted) {
				redirectAttributes.addFlashAttribute("message", "Kunde mit ID " + id + " erfolgreich gelöscht.");
			} else {
				redirectAttributes.addFlashAttribute("message", "Kunde mit ID " + id + " existiert nicht.");
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "Fehler beim Löschen des Kunden: " + e.getMessage() + " aufgetreten.");
		}
		if(location == 1) {
			return "redirect:/customers";
		}else
			return "redirect:/customers/customerlist";
	}

    /**
     * Displays a list of all customers.
     */
	@GetMapping("/customerlist")
	public String showCustomerList(Model model) {
		List<Customer> customers = customerService.findAllCustomers();
		model.addAttribute("customers", customers);
		return "customerlist";
	}
}