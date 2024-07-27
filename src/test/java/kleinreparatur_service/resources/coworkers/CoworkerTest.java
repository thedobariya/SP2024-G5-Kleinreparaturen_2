package kleinreparatur_service.resources.coworkers;

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

class CoworkerTest {

	@Mock
	private CoworkerService coworkerService;

	@Mock
	private Model model;

	@Mock
	private BindingResult bindingResult;

	@InjectMocks
	private CoworkerController coworkerController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testListCoworkers() {
		List<Coworker> coworkers = Arrays.asList(new Coworker(), new Coworker());
		when(coworkerService.findAll()).thenReturn(coworkers);

		String viewName = coworkerController.listCoworkers(model);

		assertEquals("coworkers", viewName);
		verify(model).addAttribute("coworker", coworkers);
	}

	@Test
	void testCreateForm() {
		String viewName = coworkerController.createForm(model);

		assertEquals("coworkers", viewName);
		verify(model).addAttribute(eq("coworker"), any(Coworker.class));
	}

	@Test
	void testCreateCoworker_Success() {
		Coworker coworker = new Coworker("John Doe", 20.0);
		when(bindingResult.hasErrors()).thenReturn(false);

		String viewName = coworkerController.createCoworker(coworker, bindingResult, model);

		assertEquals("redirect:/coworkers", viewName);
		verify(coworkerService).save(coworker);
	}

	@Test
	void testCreateCoworker_Error() {
		Coworker coworker = new Coworker("John Doe", -20.0);
		when(bindingResult.hasErrors()).thenReturn(true);

		String viewName = coworkerController.createCoworker(coworker, bindingResult, model);

		assertEquals("coworkers", viewName);
		verify(model).addAttribute("error", "Invalid input. Hourly rate must be non-negative.");
	}

	@Test
	void testEditForm_ExistingCoworker() {
		Long id = 1L;
		Coworker coworker = new Coworker("John Doe", 20.0);
		when(coworkerService.findById(id)).thenReturn(coworker);

		String viewName = coworkerController.editForm(id, model);

		assertEquals("coworkers", viewName);
		verify(model).addAttribute("coworker", coworker);
	}

	@Test
	void testEditForm_NonExistingCoworker() {
		Long id = 1L;
		when(coworkerService.findById(id)).thenReturn(null);

		String viewName = coworkerController.editForm(id, model);

		assertEquals("redirect:/coworkers", viewName);
	}

	@Test
	void testUpdateCoworkers_Success() {
		Long id = 1L;
		String name = "John Doe";
		double hourlyRate = 25.0;
		Coworker coworker = new Coworker(name, 20.0);
		when(coworkerService.findById(id)).thenReturn(coworker);

		String viewName = coworkerController.updateCoworkers(id, name, hourlyRate, model);

		assertEquals("redirect:/coworkers", viewName);
		verify(coworkerService).updateCoworkers(coworker);
		verify(model).addAttribute("message", "Mitarbeiter aktualisiert.");
	}

	@Test
	void testUpdateCoworkers_NonExistingCoworker() {
		Long id = 1L;
		String name = "John Doe";
		double hourlyRate = 25.0;
		when(coworkerService.findById(id)).thenReturn(null);

		String viewName = coworkerController.updateCoworkers(id, name, hourlyRate, model);

		assertEquals("redirect:/coworkers", viewName);
		verify(model).addAttribute("error", "Mitarbeiter nicht gefunden.");
	}

	@Test
	void testDeleteCoworker() {
		Long id = 1L;

		String viewName = coworkerController.deleteCoworker(id);

		assertEquals("redirect:/coworkers", viewName);
		verify(coworkerService).deleteById(id);
	}
}