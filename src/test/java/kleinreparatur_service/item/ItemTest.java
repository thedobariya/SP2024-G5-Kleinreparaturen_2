package kleinreparatur_service.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.mock.web.MockHttpSession;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * testGetAllItems: Überprüft die Methode getAllItems des ItemControllers für alle Items.
 * testGetAllOpenItems: Testet die Methode getAllItems des ItemControllers speziell für offene Items.
 * testAddItem: Prüft die Methode addItem des ItemControllers.
 * testItemServiceAddItem: Überprüft die Methode addItem des ItemServices.
 * testItemServiceGetAllOpenItems: Testet die Methode getAllOpenItems des ItemServices.
 * testItemServiceSetID: Prüft die Methode setID des ItemServices.
 */

class ItemTest {

	@Mock
	private ItemService itemService;

	@Mock
	private ItemRepository itemRepository;

	@Mock
	private Model model;

	@InjectMocks
	private ItemController itemController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllItems() {
		List<Item> items = Arrays.asList(new Item(), new Item());
		when(itemService.getAllItems()).thenReturn(items);

		String viewName = itemController.getAllItems(model, false);

		assertEquals("depot", viewName);
		verify(model).addAttribute("items", items);
		verify(model).addAttribute("totalItems", 2);
		verify(model).addAttribute("openOnly", false);
		verify(model).addAttribute(eq("item"), any(Item.class));
	}

	@Test
	void testGetAllOpenItems() {
		List<Item> openItems = Arrays.asList(new Item());
		when(itemService.getAllOpenItems()).thenReturn(openItems);

		String viewName = itemController.getAllItems(model, true);

		assertEquals("depot", viewName);
		verify(model).addAttribute("items", openItems);
		verify(model).addAttribute("totalItems", 1);
		verify(model).addAttribute("openOnly", true);
	}

	@Test
	void testAddItem() {
		Item item = new Item();
		item.setName("TestItem");
		item.setDescription("TestDescription");
		item.setCondition(2);

		Long mockId = 12345L;
		when(itemService.setID()).thenReturn(mockId);
		when(itemService.addItem(eq(mockId), anyString(), anyString(), anyInt())).thenReturn(item);

		MockHttpSession session = new MockHttpSession();
		String viewName = itemController.addItem(item, session);

		assertEquals("redirect:/cart", viewName);
		assertNotNull(session.getAttribute("newItem"));
		assertEquals(item, session.getAttribute("newItem"));
	}

	@Test
	void testItemServiceAddItem() {
		ItemService itemService = new ItemService(itemRepository);

		Long id = 1L;
		String name = "TestItem";
		String description = "TestDescription";
		Integer condition = 2;

		Item savedItem = new Item();
		savedItem.setId(id);
		savedItem.setName(name);
		savedItem.setDescription(description);
		savedItem.setCondition(condition);
		savedItem.setOpen(true);
		savedItem.setStatus(1);

		when(itemRepository.save(any(Item.class))).thenReturn(savedItem);

		Item result = itemService.addItem(id, name, description, condition);

		assertNotNull(result);
		assertEquals(id, result.getId());
		assertEquals(name, result.getName());
		assertEquals(description, result.getDescription());
		assertEquals(condition, result.getCondition());
		assertEquals(1, result.getStatus());

		verify(itemRepository).save(any(Item.class));
	}

	@Test
	void testItemServiceGetAllOpenItems() {
		ItemService itemService = new ItemService(itemRepository);

		List<Item> openItems = Arrays.asList(new Item(), new Item());
		when(itemRepository.findByIsOpenTrue()).thenReturn(openItems);

		List<Item> result = itemService.getAllOpenItems();

		assertNotNull(result);
		assertEquals(2, result.size());
		verify(itemRepository).findByIsOpenTrue();
	}

	@Test
	void testItemServiceSetID() {
		ItemService itemService = new ItemService(itemRepository);

		when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

		Long result = itemService.setID();

		assertNotNull(result);
		assertTrue(result > 0);
		verify(itemRepository).findById(anyLong());
	}
}