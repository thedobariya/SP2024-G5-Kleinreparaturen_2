package kleinreparatur_service.resources.workingstation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkingstationTest {

	@Mock
	private WorkingstationService workingstationService;

	@Mock
	private Model model;

	@Mock
	private BindingResult bindingResult;

	@InjectMocks
	private WorkingstationController workingstationController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testShowWorkingstationsList() {
		List<Workingstation> workingstations = Arrays.asList(new Workingstation(), new Workingstation());
		when(workingstationService.findAllWorkingstations()).thenReturn(workingstations);

		String viewName = workingstationController.showWorkingstationsList(model);

		assertEquals("workingstations", viewName);
		verify(model).addAttribute("workingstations", workingstations);
	}

	@Test
	void testCreateWorkingstation_Success() {
		Workingstation workingstation = new Workingstation("Test Station", 100.0);
		when(bindingResult.hasErrors()).thenReturn(false);
		when(workingstationService.save(workingstation)).thenReturn(workingstation);

		String viewName = workingstationController.createWorkingstation(workingstation, bindingResult, model);

		assertEquals("redirect:/workingstations", viewName);
		verify(workingstationService).save(workingstation);
		verify(model).addAttribute(eq("message"), contains("Workingstation created with ID:"));
	}

	@Test
	void testCreateWorkingstation_Error() {
		Workingstation workingstation = new Workingstation("Test Station", -100.0);
		when(bindingResult.hasErrors()).thenReturn(true);

		String viewName = workingstationController.createWorkingstation(workingstation, bindingResult, model);

		assertEquals("workingstations", viewName);
		verify(model).addAttribute("error", "Invalid input. Price must be non-negative.");
	}

	@Test
	void testUpdateWorkingstation_Success() {
		Long id = 1L;
		String name = "Updated Station";
		double price = 150.0;
		Workingstation workingstation = new Workingstation(name, 100.0);
		workingstation.setId(id);
		when(workingstationService.findById(id)).thenReturn(workingstation);

		String viewName = workingstationController.updateWorkingstation(id, name, price, model);

		assertEquals("redirect:/workingstations", viewName);
		verify(workingstationService).updateWorkingstations(workingstation);
		verify(model).addAttribute("message", "Workingstation updated successfully.");
	}

	@Test
	void testDeleteWorkingstation() {
		Long id = 1L;

		String viewName = workingstationController.deleteWorkingstation(id);

		assertEquals("redirect:/workingstations", viewName);
		verify(workingstationService).deleteById(id);
	}
}