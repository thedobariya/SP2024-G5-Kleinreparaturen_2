package kleinreparatur_service.resources.workingstation;

import jakarta.validation.Valid;
import kleinreparatur_service.resources.coworkers.Coworker;
import kleinreparatur_service.resources.workingstation.Workingstation;
import kleinreparatur_service.resources.workingstation.WorkingstationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing workingstation-related operations.
 * Requires authentication for all endpoints.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/workingstations")
public class WorkingstationController {

	private final WorkingstationService workingstationService;

    /**
     * Constructor for WorkingstationController.
     * @param workingstationService The service for workingstation operations.
     */
	@Autowired
	public WorkingstationController(WorkingstationService workingstationService) {
		this.workingstationService = workingstationService;
	}

    /**
     * Displays a list of all workingstations.
     * @param model The Model object to add attributes to.
     * @return The name of the view to render.
     */
	@GetMapping
	public String showWorkingstationsList(Model model) {
		List<Workingstation> workingstations = workingstationService.findAllWorkingstations();
		model.addAttribute("workingstations", workingstations);
		return "workingstations";
	}

    /**
     * Displays the form for creating a new workingstation.
     * @param model The Model object to add attributes to.
     * @return The name of the view to render.
     */
	@GetMapping("/create")
	public String createWorkingstationForm(Model model) {
		model.addAttribute("workingstation", new Workingstation());
		return "workingstations";
	}

    /**
     * Handles the creation of a new workingstation.
     * @param workingstation The workingstation to create.
     * @param result The BindingResult for validation errors.
     * @param model The Model object to add attributes to.
     * @return The name of the view to render or a redirect.
     */
	@PostMapping("/create")
	public String createWorkingstation(@Valid @ModelAttribute Workingstation workingstation, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("error", "Invalid input. Price must be non-negative.");
			return "workingstations";
		}
		try {
			Workingstation savedWorkingstation = workingstationService.save(workingstation);
			model.addAttribute("message", "Workingstation created with ID: " + savedWorkingstation.getId());
		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			return "workingstations";
		}
		return "redirect:/workingstations";
	}

    /**
     * Displays the form for editing an existing workingstation.
     * @param id The ID of the workingstation to edit.
     * @param model The Model object to add attributes to.
     * @return The name of the view to render.
     */
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id, Model model) {
		model.addAttribute("workingstations", workingstationService.findById(id));
		return "workingstations";
	}

    /**
     * Handles the updating of an existing workingstation.
     * @param id The ID of the workingstation to update.
     * @param name The new name for the workingstation.
     * @param price The new price for the workingstation.
     * @param model The Model object to add attributes to.
     * @return A redirect to the workingstations list.
     */

	@PostMapping("/edit/{id}")
	public String updateWorkingstation(@PathVariable("id") Long id,
									   @RequestParam("name") String name,
									   @RequestParam("price") double price,
									   Model model) {
		try {
			Workingstation workingstation = workingstationService.findById(id);
			if (workingstation != null) {
				workingstation.setName(name);
				workingstation.setPrice(price);
				workingstationService.updateWorkingstations(workingstation);
				model.addAttribute("message", "Workingstation updated successfully.");
			} else {
				model.addAttribute("error", "Workingstation not found.");
			}
		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
		}
		return "redirect:/workingstations";
	}

    /**
     * Handles the deletion of a workingstation.
     * @param id The ID of the workingstation to delete.
     * @return A redirect to the workingstations list.
     */
	@GetMapping("/delete/{id}")
	public String deleteWorkingstation(@PathVariable Long id) {
		workingstationService.deleteById(id);
		return "redirect:/workingstations";
	}

    /**
     * Handles the updating of a workingstation's price.
     * @param id The ID of the workingstation to update.
     * @param price The new price for the workingstation.
     * @param model The Model object to add attributes to.
     * @return A redirect to the workingstations list.
     */
	@PostMapping("/update")
	public String updateWorkingstationPrice(@RequestParam("id") Long id, @RequestParam("price") double price, Model model) {
		try {
			workingstationService.updatePrice(id, price);
			model.addAttribute("message", "Prise aktualisiert.");
		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
		}
		return "redirect:/workingstations";
	}

}
