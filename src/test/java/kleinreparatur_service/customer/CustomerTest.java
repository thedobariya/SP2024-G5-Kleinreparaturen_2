package kleinreparatur_service.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerTest {

	@Mock
	private CustomerService customerService;

	@Mock
	private Model model;

	@Mock
	private RedirectAttributes redirectAttributes;

	@InjectMocks
	private CustomerController customerController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateCustomerForm() {
		String viewName = customerController.createCustomerForm(model);

		assertEquals("customer", viewName);
		verify(model).addAttribute(eq("customer"), any(Customer.class));
	}

	@Test
	void testSaveCustomer() throws Exception {
		String firstName = "John";
		String lastName = "Doe";
		String dateOfBirthStr = "1990-01-01";
		Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirthStr);

		when(customerService.findByFirstNameLastNameAndDateOfBirth(firstName, lastName, dateOfBirth))
			.thenReturn(Optional.empty());

		Customer savedCustomer = new Customer(firstName, lastName, dateOfBirth);
		savedCustomer.setId(1L);
		when(customerService.save(any(Customer.class))).thenReturn(savedCustomer);

		String viewName = customerController.saveCustomer(firstName, lastName, dateOfBirthStr, model);

		assertEquals("customer", viewName);
		verify(model).addAttribute("message", "Kunde mit ID 1 erfolgreich erstellt.");
	}

	@Test
	void testSearchCustomer() throws Exception {
		String firstName = "John";
		String lastName = "Doe";
		String dateOfBirthStr = "1990-01-01";
		Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirthStr);

		Customer foundCustomer = new Customer(firstName, lastName, dateOfBirth);
		foundCustomer.setId(1L);
		when(customerService.findByFirstNameLastNameAndDateOfBirth(firstName, lastName, dateOfBirth))
			.thenReturn(Optional.of(foundCustomer));

		String viewName = customerController.searchCustomer(firstName, lastName, dateOfBirthStr, model);

		assertEquals("customer", viewName);
		verify(model).addAttribute("message", "Kunde mit ID gefunden: 1");
	}

	@Test
	void testSearchCustomerById() {
		Long id = 1L;
		Customer customer = new Customer("John", "Doe", new Date());
		customer.setId(id);
		when(customerService.findById(id)).thenReturn(Optional.of(customer));

		String viewName = customerController.searchCustomerById(id, model);

		assertEquals("customer", viewName);
		verify(model).addAttribute(eq("customer"), any(Customer.class));
		verify(model).addAttribute(eq("message"), contains("Kunde mit ID 1 gefunden."));
	}

	@Test
	void testDeleteCustomer() {
		Long id = 1L;
		when(customerService.deleteById(id)).thenReturn(true);

		String viewName = customerController.deleteCustomer(id, 1, model, redirectAttributes);

		assertEquals("redirect:/customers", viewName);
		verify(redirectAttributes).addFlashAttribute("message", "Kunde mit ID 1 erfolgreich gelöscht.");
	}

	@Test
	void testShowCustomerList() {
		List<Customer> customers = Arrays.asList(new Customer(), new Customer());
		when(customerService.findAllCustomers()).thenReturn(customers);

		String viewName = customerController.showCustomerList(model);

		assertEquals("customerlist", viewName);
		verify(model).addAttribute("customers", customers);
	}
}