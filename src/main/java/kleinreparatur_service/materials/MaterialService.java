package kleinreparatur_service.materials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing material-related operations.
 */
@Service
public class MaterialService {

	private final MaterialRepository materialRepository;

	/**
	 * Constructor for MaterialService.
	 * @param materialRepository The repository for material data operations.
	 */
	@Autowired
	public MaterialService(MaterialRepository materialRepository) {
		this.materialRepository = materialRepository;
	}

	/**
	 * Retrieves all materials from the database.
	 * @return A list of all materials.
	 */
	public List<Material> findAllMaterials() {
		return materialRepository.findAll();
	}

	/**
	 * Saves a new material to the database.
	 * @param material The material to save.
	 * @return The saved material.
	 */
	public Material saveMaterial(Material material) {
		return materialRepository.save(material);
	}

	/**
	 * Updates an existing material in the database.
	 * @param material The material to update.
	 * @return The updated material.
	 * @throws IllegalArgumentException if the material has invalid data.
	 */
	public Material updateMaterial(Material material) {
		if (material.getPrice() < 0) {
			throw new IllegalArgumentException("Price cannot be negative");
		}
		if (material.getStock() < 0) {
			throw new IllegalArgumentException("Stock cannot be negative");
		}
		if (material.getUnit() == null || material.getUnit().isEmpty()) {
			throw new IllegalArgumentException("Unit cannot be empty");
		}
		return materialRepository.save(material);
	}

	/**
	 * Deletes a material from the database by its ID.
	 * @param id The ID of the material to delete.
	 */
	public void deleteMaterial(Long id) {
		materialRepository.deleteById(id);
	}

	/**
	 * Finds a material by its ID.
	 * @param id The ID of the material to find.
	 * @return The found material.
	 * @throws IllegalArgumentException if no material is found with the given ID.
	 */
	public Material findById(Long id) {
		return materialRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid material ID: " + id));
	}

	/**
	 * Updates the stock of a material.
	 * @param id The ID of the material to update.
	 * @param quantity The quantity to add to the current stock.
	 * @return The updated material.
	 * @throws IllegalArgumentException if no material is found with the given ID.
	 */
	public Material updateMaterialStock(Long id, int quantity) {
		Material material = materialRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid material ID: " + id));
		material.setStock(material.getStock() + quantity);
		return materialRepository.save(material);
	}

	/**
	 * Reduces the stock of a material.
	 * @param id The ID of the material to update.
	 * @param quantity The quantity to reduce from the current stock.
	 * @throws IllegalStateException if there's not enough stock.
	 */
	public void reduceMaterialStock(Long id, int quantity) {
		materialRepository.findById(id).ifPresent(material -> {
			int newStock = material.getStock() - quantity;
			if (newStock < 0) {
				throw new IllegalStateException("Not enough stock for material: " + material.getName());
			}
			material.setStock(newStock);
			materialRepository.save(material);
		});
	}

	/**
	 * Finds multiple materials by their IDs.
	 * @param materialIds A list of material IDs to find.
	 * @return A list of found materials.
	 */
	public List<Material> findAllById(List<Long> materialIds) {
		return materialRepository.findAllById(materialIds);
	}
}