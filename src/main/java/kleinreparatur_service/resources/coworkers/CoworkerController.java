package kleinreparatur_service.resources.coworkers;

import jakarta.validation.Valid;
import kleinreparatur_service.materials.Material;
import kleinreparatur_service.resources.coworkers.Coworker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing coworker-related operations.
 * Requires authentication for all endpoints.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/coworkers")
public class CoworkerController {

	private final CoworkerService coworkerService;

	/**
	 * Constructor for CoworkerController.
	 * @param coworkerService The service for coworker operations.
	 */
	@Autowired
	public CoworkerController(CoworkerService coworkerService) {
		this.coworkerService = coworkerService;
	}

	/**
	 * Displays a list of all coworkers.
	 * @param model The Model object to add attributes to.
	 * @return The name of the view to render.
	 */
	@GetMapping
	public String listCoworkers(Model model) {
		model.addAttribute("coworker", coworkerService.findAll());
		return "coworkers";

	}

	/**
	 * Displays the form for creating a new coworker.
	 * @param model The Model object to add attributes to.
	 * @return The name of the view to render.
	 */
	@GetMapping("/create")
	public String createForm(Model model) {
		model.addAttribute("coworker", new Coworker());
		return "coworkers";
	}

	/**
	 * Handles the creation of a new coworker.
	 * @param coworker The coworker to create.
	 * @param result The BindingResult for validation errors.
	 * @param model The Model object to add attributes to.
	 * @return The name of the view to render or a redirect.
	 */
	@PostMapping("/create")
	public String createCoworker(@Valid @ModelAttribute Coworker coworker, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("error", "Invalid input. Hourly rate must be non-negative.");
			return "coworkers";
		}
		try {
			coworkerService.save(coworker);
		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			return "coworkers";
		}
		return "redirect:/coworkers";
	}

	/**
	 * Displays the form for editing an existing coworker.
	 * @param id The ID of the coworker to edit.
	 * @param model The Model object to add attributes to.
	 * @return The name of the view to render or a redirect.
	 */
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id, Model model) {
		Coworker coworker = coworkerService.findById(id);
		if (coworker != null) {
			model.addAttribute("coworker", coworker);
			return "coworkers";
		}
		return "redirect:/coworkers";
	}

	/**
	 * Handles the updating of an existing coworker.
	 * @param id The ID of the coworker to update.
	 * @param name The new name for the coworker.
	 * @param hourlyRate The new hourly rate for the coworker.
	 * @param model The Model object to add attributes to.
	 * @return A redirect to the coworkers list.
	 */
	@PostMapping("/edit/{id}")
	public String updateCoworkers(@PathVariable("id") Long id,
								  @RequestParam("name") String name,
								  @RequestParam("hourlyRate") double hourlyRate,
								  Model model) {
		try {
			Coworker coworker = coworkerService.findById(id);
			if (coworker != null) {
				coworker.setName(name);
				coworker.setHourlyRate(hourlyRate);
				coworkerService.updateCoworkers(coworker);
				model.addAttribute("message", "Mitarbeiter aktualisiert.");
			} else {
				model.addAttribute("error", "Mitarbeiter nicht gefunden.");
			}
		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
		}
		return "redirect:/coworkers";
	}

	/**
	 * Handles the deletion of a coworker.
	 * @param id The ID of the coworker to delete.
	 * @return A redirect to the coworkers list.
	 */
	@GetMapping("/delete/{id}")
	public String deleteCoworker(@PathVariable Long id) {
		coworkerService.deleteById(id);
		return "redirect:/coworkers";
	}

	/**
	 * Handles the updating of a coworker's hourly rate.
	 * @param id The ID of the coworker to update.
	 * @param hourlyRate The new hourly rate for the coworker.
	 * @param model The Model object to add attributes to.
	 * @return A redirect to the coworkers list.
	 */
	@PostMapping("/update")
	public String updateCoworkers(@RequestParam("id") Long id, @RequestParam(value = "hourlyRate") double hourlyRate, Model model) {
		try {
			coworkerService.updateHourlyRate(id, hourlyRate);
			model.addAttribute("message", "Stunden lohn aktualisiert.");
		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
		}
		return "redirect:/coworkers";
	}
}
