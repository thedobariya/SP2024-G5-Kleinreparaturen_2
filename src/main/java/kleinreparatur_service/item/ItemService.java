package kleinreparatur_service.item;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import java.util.Optional;
import java.util.Random;

@Service
public class ItemService {

	private static ItemRepository itemRepository;

	@Autowired
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	public static Optional<Item> findById(long itemId) {
		return itemRepository.findById(itemId);
	}

	public List<Item> findAllItems() {
		return itemRepository.findAll();
	}

	public Item addItem(Long id, String name, String description, Integer condition) {
		Item tempitem = new Item();
		tempitem.setId(id);
		tempitem.setName(name);
		tempitem.setDescription(description);
		tempitem.setCondition(condition);
		tempitem.setOpen(true);
		tempitem.setStatus(1);
		return itemRepository.save(tempitem);
	}

	public Item deleteItem(Integer id) {
		Item item = itemRepository.findById(id).orElse(null);
		if (item != null) {
			itemRepository.delete(item);
		}
		return item;
	}

	public List<Item> getAllItems() {
		return itemRepository.findAll();
	}

	public List<Item> getAllOpenItems() {
		return itemRepository.findByIsOpenTrue();
	}

	/**
	 * Prefills the database with sample items.
	 */
	public Item prefillData() {
		Item item1 = new Item();
		item1.setId(this.setID());
		item1.setName("Item");
		item1.setDescription("Description");
		item1.setCondition(1);
		item1.setOpen(true);
		item1.setStatus(1);
		itemRepository.save(item1);
		Item item2 = new Item();
		item2.setId(this.setID());
		item2.setName("Item");
		item2.setDescription("Description");
		item2.setCondition(2);
		item2.setOpen(true);
		item2.setStatus(2);
		itemRepository.save(item2);
		Item item3 = new Item();
		item3.setId(this.setID());
		item3.setName("Item");
		item3.setDescription("Description");
		item3.setCondition(3);
		item3.setOpen(false);
		item3.setStatus(3);
		itemRepository.save(item3);
		return item1;
	}

	/**
	 * Generates a unique ID for an item.
	 */
	public Long setID() {
		String id = String.format("%04d%s", new Random().nextInt(9999), System.currentTimeMillis());
		// Ergebnis: "56.781.234.567.890.123" (4 Zufallsziffern + 13-stelliger Zeitstempel)

		/**
		 * erstellt eine unique ID für ein Item
		 * und sucht danach in der Datenbank, ob es breits ein Item mit der ID gibt
		 * wenn ja, dann wird die Methode rekursiv aufgerufen
		 * wenn nein, wird die ID zurückgegeben
		 * @return Long
		 */

		itemRepository.findById(Long.parseLong(id)).ifPresent(item -> setID());
		return Long.parseLong(id);
	}
}