package kleinreparatur_service.servicecatalog;

import kleinreparatur_service.materials.Material;
import kleinreparatur_service.resources.workingstation.Workingstation;
import org.javamoney.moneta.Money;
import org.salespointframework.core.DataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import java.util.*;

import static org.salespointframework.core.Currencies.EURO;

@Component
@Order(20)
public class ServiceCatalogDataInitializer implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceCatalogDataInitializer.class);

	private final ServiceCatalog serviceCatalog;

	public ServiceCatalogDataInitializer(ServiceCatalog serviceCatalog) {
		Assert.notNull(serviceCatalog, "ServiceCatalog must not be null!");
		this.serviceCatalog = serviceCatalog;
	}

	@Override
	public void initialize() {
		List<Workingstation> emptyWorkingsstationList = new ArrayList<>();
		List<Material> emptyMaterialsList = new ArrayList<>();

		serviceCatalog.save(new Service("Absätze", "Absätze", Money.of(1, EURO), Service.ServiceType.FLICKSCHUSTEREI, 1,emptyWorkingsstationList,emptyMaterialsList));
		//serviceCatalog.save(new Service("Sohlen", "Sohlen", Money.of(2, EURO), Service.ServiceType.FLICKSCHUSTEREI, 1.5,emptyWorkingsstationList,emptyMaterialsList));
		//serviceCatalog.save(new Service("Nähte", "Nähte", Money.of(3, EURO), Service.ServiceType.FLICKSCHUSTEREI, 2.5,emptyWorkingsstationList,emptyMaterialsList));

		serviceCatalog.save(new Service("Knöpfe", "Knöpfe", Money.of(4, EURO), Service.ServiceType.NAEHSERVICE, 1.5,emptyWorkingsstationList,emptyMaterialsList));
		//serviceCatalog.save(new Service("Nähte", "Nähte", Money.of(5, EURO), Service.ServiceType.NAEHSERVICE, 1.5,emptyWorkingsstationList,emptyMaterialsList));
		//serviceCatalog.save(new Service("Flicken", "Flicken", Money.of(6, EURO), Service.ServiceType.NAEHSERVICE, 1.5,emptyWorkingsstationList,emptyMaterialsList));

		serviceCatalog.save(new Service("Schlüssel kopieren", "Schlüssel kopieren", Money.of(7, EURO), Service.ServiceType.SCHLUESSELDIENST, 1.5,emptyWorkingsstationList,emptyMaterialsList));
		//serviceCatalog.save(new Service("Schilder gravieren", "Schilder gravieren", Money.of(8, EURO), Service.ServiceType.SCHLUESSELDIENST, 1.5,emptyWorkingsstationList,emptyMaterialsList));

		serviceCatalog.save(new Service("Wäsche", "Wäsche", Money.of(9, EURO), Service.ServiceType.TEXTILSERVICE, 1.5,emptyWorkingsstationList,emptyMaterialsList));
		//serviceCatalog.save(new Service("Anzüge", "Anzüge", Money.of(10, EURO), Service.ServiceType.TEXTILSERVICE, 1.5,emptyWorkingsstationList,emptyMaterialsList));
		//serviceCatalog.save(new Service("Leder", "Leder", Money.of(11, EURO), Service.ServiceType.TEXTILSERVICE, 1.5,emptyWorkingsstationList,emptyMaterialsList));

		serviceCatalog.save(new Service("Kabel ersetzen", "Kabel ersetzen", Money.of(12, EURO), Service.ServiceType.ELEKTROWERKSTATT, 1.5,emptyWorkingsstationList,emptyMaterialsList));
		//serviceCatalog.save(new Service("Kabel löten", "Kabel löten", Money.of(13, EURO), Service.ServiceType.ELEKTROWERKSTATT, 1.5,emptyWorkingsstationList,emptyMaterialsList));

		serviceCatalog.save(new Service("Scheren schärfen", "Scheren", Money.of(14, EURO), Service.ServiceType.METALLARBEITEN, 1.5,emptyWorkingsstationList,emptyMaterialsList));
		//serviceCatalog.save(new Service("Messer schärfen", "Messer", Money.of(15, EURO), Service.ServiceType.METALLARBEITEN, 1.5,emptyWorkingsstationList,emptyMaterialsList));
	}
}
