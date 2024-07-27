package kleinreparatur_service.materials;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MaterialsTest {

	@Mock
	private MaterialService materialService;

	@Mock
	private Model model;

	@InjectMocks
	private MaterialController materialController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testShowMaterialList() {
		List<Material> materials = Arrays.asList(new Material(), new Material());
		when(materialService.findAllMaterials()).thenReturn(materials);

		String viewName = materialController.showMaterialList(model);

		assertEquals("materials", viewName);
		verify(model).addAttribute("materials", materials);
	}

	@Test
	void testShowCreateForm() {
		String viewName = materialController.showCreateForm(model);

		assertEquals("materials", viewName);
		verify(model).addAttribute(eq("material"), any(Material.class));
	}

	@Test
	void testCreateMaterial() {
		String name = "Test Material";
		double price = 10.0;
		int stock = 100;
		String unit = "pcs";

		String viewName = materialController.createMaterial(name, price, stock, unit, model);

		assertEquals("redirect:/materials", viewName);
		verify(materialService).saveMaterial(any(Material.class));
	}

	@Test
	void testShowEditForm() {
		Long id = 1L;
		Material material = new Material("Test", 10.0, 100, "pcs");
		when(materialService.findById(id)).thenReturn(material);

		String viewName = materialController.showEditForm(id, model);

		assertEquals("redirect:/materials", viewName);
		verify(model).addAttribute("material", material);
	}

	@Test
	void testUpdateMaterial() {
		Long id = 1L;
		String name = "Updated Material";
		double price = 15.0;
		int stock = 150;

		Material material = new Material(name, price, stock, "pcs");
		when(materialService.findById(id)).thenReturn(material);

		String viewName = materialController.updateMaterial(id, name, price, stock, model);

		assertEquals("redirect:/materials", viewName);
		verify(materialService).updateMaterial(material);
	}

	@Test
	void testDeleteMaterial() {
		Long id = 1L;

		String viewName = materialController.deleteMaterial(id, model);

		assertEquals("redirect:/materials", viewName);
		verify(materialService).deleteMaterial(id);
	}

	@Test
	void testUpdateMaterialStock() {
		Long id = 1L;
		Double newPrice = 20.0;
		Integer newStock = 200;
		String newUnit = "kg";

		Material material = new Material("Test", 10.0, 100, "pcs");
		when(materialService.findById(id)).thenReturn(material);

		String viewName = materialController.updateMaterial(id, newPrice, newStock, newUnit, model);

		assertEquals("redirect:/materials", viewName);
		verify(materialService).updateMaterial(material);
		assertEquals(newPrice, material.getPrice());
		assertEquals(newStock, material.getStock());
		assertEquals(newUnit, material.getUnit());
	}

	@Test
	void testUpdateMaterialStockWithInvalidId() {
		Long id = 999L;
		when(materialService.findById(id)).thenReturn(null);

		String viewName = materialController.updateMaterial(id, 20.0, 200, "kg", model);

		assertEquals("redirect:/materials", viewName);
		verify(model).addAttribute(eq("error"), contains("Invalid material ID"));
	}
}