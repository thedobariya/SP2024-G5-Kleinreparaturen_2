package kickstart.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping()
	public String createCustomerForm(Model model) {
		model.addAttribute("customer", new Customer());
		return "customer";
	}

	@PostMapping("/create")
	public String saveCustomer(@RequestParam("firstName") String firstName,
							   @RequestParam("lastName") String lastName,
							   @RequestParam("dateOfBirth") String dateOfBirthStr,
							   Model model) {
		try {
			Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirthStr);
			Optional<Customer> customerOpt = customerService.findByFirstNameLastNameAndDateOfBirth(firstName, lastName, dateOfBirth);
			if (customerOpt.isPresent()) {
				model.addAttribute("message", "Customer already exist and the customer ID: " + customerOpt.get().getId());
			}else {
				Customer customer = new Customer(firstName, lastName, dateOfBirth);
				Customer savedCustomer = customerService.save(customer);
				model.addAttribute("message", "Customer created with ID is: " + savedCustomer.getId());
			}
		} catch (Exception e) {
			model.addAttribute("message", "Error creating customer: " + e.getMessage());
		}
		return "customer";
	}

	@PostMapping("/search")
	public String searchCustomer(@RequestParam("firstName") String firstName,
								 @RequestParam("lastName") String lastName,
								 @RequestParam("dateOfBirth") String dateOfBirthStr,
								 Model model) {
		try {
			Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirthStr);
			Optional<Customer> customerOpt = customerService.findByFirstNameLastNameAndDateOfBirth(firstName, lastName, dateOfBirth);
			if (customerOpt.isPresent()) {
				model.addAttribute("message", "Customer found with ID: " + customerOpt.get().getId());
			} else {
				model.addAttribute("message", "Customer doesn't exist.");
			}
		} catch (Exception e) {
			model.addAttribute("message", "Error searching for customer: " + e.getMessage());
		}
		return "customer";
	}
}
