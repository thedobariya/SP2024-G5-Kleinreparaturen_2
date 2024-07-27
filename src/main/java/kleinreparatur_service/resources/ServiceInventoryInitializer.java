/*
package kleinreparatur_service.resources;

import kleinreparatur_service.servicecatalog.ServiceCatalog;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Order(30)
public class ServiceInventoryInitializer implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceInventoryInitializer.class);

	private final UniqueInventory<UniqueInventoryItem> serviceInventory;
	private final ServiceCatalog serviceCatalog;

	ServiceInventoryInitializer(UniqueInventory<UniqueInventoryItem> serviceInventory, ServiceCatalog serviceCatalog) {
		Assert.notNull(serviceInventory, "Inventory must not be null!");
		Assert.notNull(serviceCatalog, "ServiceCatalog must not be null!");

		this.serviceInventory = serviceInventory;
		this.serviceCatalog = serviceCatalog;
	}

	@Override
	public void initialize() {

		serviceCatalog.findAll().forEach(service -> {
			LOG.info("Initializing inventory for service: " + service.getName());

			serviceInventory.findByProduct(service).ifPresentOrElse(
				item -> LOG.info("Inventory item already exists for service: " + service.getName()),
				() -> {
					serviceInventory.save(new UniqueInventoryItem(service, Quantity.of(10)));
					LOG.info("Created inventory item for service: " + service.getName());
				}
			);
		});
	}
}

*/

package kleinreparatur_service.resources;

import kleinreparatur_service.servicecatalog.ServiceCatalog;
import org.eclipse.jdt.internal.compiler.ast.Javadoc;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Order(30)
public class ServiceInventoryInitializer implements DataInitializer {
	private final UniqueInventory<UniqueInventoryItem> serviceInventory;
	private final ServiceCatalog serviceCatalog;

	ServiceInventoryInitializer(UniqueInventory<UniqueInventoryItem> serviceInventory, ServiceCatalog serviceCatalog) {

		Assert.notNull(serviceInventory, "Inventory must not be null!");
		Assert.notNull(serviceCatalog, "VideoCatalog must not be null!");

		this.serviceInventory = serviceInventory;
		this.serviceCatalog = serviceCatalog;
	}

	@Override
	public void initialize() {
		serviceCatalog.findAll().forEach(service -> {
			if (serviceInventory.findByProduct(service).isEmpty()) {
				serviceInventory.save(new UniqueInventoryItem(service, Quantity.of(10)));
			} else {
				System.out.println("Inventory already initialized for service: " + service.getId());
			}
		});
	}
}
