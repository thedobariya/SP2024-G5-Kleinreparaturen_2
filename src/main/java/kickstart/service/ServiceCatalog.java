package kickstart.service;

import org.salespointframework.catalog.Catalog;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Streamable;

public interface ServiceCatalog extends Catalog<Service> {
	static final Sort DEFAULT_SORT = Sort.sort(Service.class).by(Service::getId).descending();

	Streamable<Service> findByType(Service.ServiceType type, Sort sort);

	default Streamable<Service> findByType(Service.ServiceType type) {
		return findByType(type, DEFAULT_SORT);
	}
}
