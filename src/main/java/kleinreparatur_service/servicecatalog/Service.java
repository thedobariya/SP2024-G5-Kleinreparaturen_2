package kleinreparatur_service.servicecatalog;

import jakarta.persistence.*;
import kleinreparatur_service.materials.Material;
import kleinreparatur_service.resources.workingstation.Workingstation;
import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity
public class Service extends Product{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany
	private List<Workingstation> workingstations;

	@ElementCollection
	@CollectionTable(name = "service_material_quantities", joinColumns = @JoinColumn(name = "service_id"))
	@MapKeyJoinColumn(name = "material_id")
	@Column(name = "quantity")
	private Map<Material, Integer> materialQuantities = new HashMap<>();

	private String name;
	private String description;

	@NonNull
	private Money price;
	private ServiceType type;

	@NonNull
	private double workload;

	@ManyToMany
	private List<Material> materials;

	public enum ServiceType {
		FLICKSCHUSTEREI, NAEHSERVICE, SCHLUESSELDIENST, TEXTILSERVICE, ELEKTROWERKSTATT, METALLARBEITEN
	}
	
	public Service() {}

	public Service(String name, String description, Money moneyPrice, ServiceType type, double workload, List<Workingstation> workingstations, List<Material> materials) {
		super(name, moneyPrice);
		this.name = name;
		this.description = description;
		this.price = moneyPrice;
		this.type = type;
		this.workload = workload;
		this.workingstations = workingstations;
		this.materials = materials;
	}

	public void setMaterialQuantity(Material material, int quantity) {
		materialQuantities.put(material, quantity);
	}

	public Long getServiceId() {
		return id;
	}

	public String getServiceName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Money getServicePrice() {
		return price;
	}

	public ServiceType getType() {
		return type;
	}

	public double getWorkload() {
		return workload;
	}

	public List<Workingstation> getWorkingstations() {
		return workingstations;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setServiceName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setServicePrice(Money price) {
		this.price = price;
	}

	public void setType(ServiceType type) {
		this.type = type;
	}

	public void setWorkload(double workload) {
		this.workload = workload;
	}

	public void setWorkingstations(List<Workingstation> workingstations) {
		this.workingstations = workingstations;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	public Map<Material, Integer> getMaterialQuantities() {
		return materialQuantities;
	}

	public void setMaterialQuantities(Map<Material, Integer> materialQuantities) {
		this.materialQuantities = materialQuantities;
	}
}
