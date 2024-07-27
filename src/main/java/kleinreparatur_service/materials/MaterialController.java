package kleinreparatur_service.materials;

import kleinreparatur_service.materials.Material;
import kleinreparatur_service.materials.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling material-related HTTP requests.
 * Requires authentication for all endpoints.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/materials")
public class MaterialController {

	private final MaterialService materialService;

	/**
	 * Constructor for MaterialController.
	 * @param materialService The service for material operations.
	 */
	@Autowired
	public MaterialController(MaterialService materialService) {
		this.materialService = materialService;
	}

	/**
	 * Displays a list of all materials.
	 * @param model The Model object to add attributes to.
	 * @return The name of the view to render.
	 */
	@GetMapping
	public String showMaterialList(Model model) {
		List<Material> materials = materialService.findAllMaterials();
		model.addAttribute("materials", materials);
		return "materials";
	}

	/**
	 * Displays the form for creating a new material.
	 * Only accessible to users with MANAGEMENTLEAD role.
	 * @param model The Model object to add attributes to.
	 * @return The name of the view to render.
	 */
	@PreAuthorize("hasRole('MANAGEMENTLEAD')")
	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("material", new Material());
		return "materials";
	}

	/**
	 * Handles the creation of a new material.
	 * Only accessible to users with MANAGEMENTLEAD role.
	 * @param name The name of the material.
	 * @param price The price of the material.
	 * @param stock The stock quantity of the material.
	 * @param unit The unit of measurement for the material.
	 * @param model The Model object to add attributes to.
	 * @return A redirect to the materials list.
	 */
	@PostMapping("/create")
	@PreAuthorize("hasRole('MANAGEMENTLEAD')")
	public String createMaterial(@RequestParam("name") String name,
								 @RequestParam("price") double price,
								 @RequestParam("stock") int stock,
								 @RequestParam("unit") String unit,
								 Model model) {
		Material material = new Material(name, price, stock, unit);
		materialService.saveMaterial(material);
		return "redirect:/materials";
	}

	/**
	 * Displays the form for editing a material.
	 * @param id The ID of the material to edit.
	 * @param model The Model object to add attributes to.
	 * @return A redirect to the materials list.
	 */
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") Long id, Model model) {
		Material material = materialService.findById(id);
		model.addAttribute("material", material);
		return "redirect:/materials";
	}

	/**
	 * Handles the updating of a material.
	 * Only accessible to users with MANAGEMENTLEAD role.
	 * @param id The ID of the material to update.
	 * @param name The new name of the material.
	 * @param price The new price of the material.
	 * @param stock The new stock quantity of the material.
	 * @param model The Model object to add attributes to.
	 * @return A redirect to the materials list.
	 */
	@PostMapping("/edit/{id}")
	@PreAuthorize("hasRole('MANAGEMENTLEAD')")
	public String updateMaterial(@PathVariable("id") Long id,
								 @RequestParam("name") String name,
								 @RequestParam("price") double price,
								 @RequestParam("stock") int stock,
								 Model model) {
		Material material = materialService.findById(id);
		material.setName(name);
		material.setPrice(price);
		material.setStock(stock);
		materialService.updateMaterial(material);
		return "redirect:/materials";
	}

	/**
	 * Handles the deletion of a material.
	 * @param id The ID of the material to delete.
	 * @param model The Model object to add attributes to.
	 * @return A redirect to the materials list.
	 */
	@GetMapping("/delete/{id}")
	public String deleteMaterial(@PathVariable("id") Long id, Model model) {
		materialService.deleteMaterial(id);
		return "redirect:/materials";
	}

	/**
	 * Handles the updating of material stock.
	 * Only accessible to users with MANAGEMENTLEAD role.
	 * @param id The ID of the material to update.
	 * @param price The new price of the material (optional).
	 * @param stock The new stock quantity of the material (optional).
	 * @param unit The new unit of measurement for the material (optional).
	 * @param model The Model object to add attributes to.
	 * @return A redirect to the materials list.
	 */
	@PostMapping("/updateStock")
	@PreAuthorize("hasRole('MANAGEMENTLEAD')")
	public String updateMaterial(@RequestParam("id") Long id,
								 @RequestParam(value = "price", required = false) Double price,
								 @RequestParam(value = "stock", required = false) Integer stock,
								 @RequestParam(value = "unit", required = false) String unit,
								 Model model) {
		try {
			Material material = materialService.findById(id);
			if (material == null) {
				throw new IllegalArgumentException("Invalid material ID: " + id);
			}

			if (price != null) {
				material.setPrice(price);
			}
			if (stock != null) {
				material.setStock(stock);
			}
			if (unit != null) {
				material.setUnit(unit);
			}

			materialService.updateMaterial(material);
			model.addAttribute("message", "Material updated successfully.");
		} catch (Exception e) {
			model.addAttribute("error", "Error updating material: " + e.getMessage());
		}
		return "redirect:/materials";
	}
}