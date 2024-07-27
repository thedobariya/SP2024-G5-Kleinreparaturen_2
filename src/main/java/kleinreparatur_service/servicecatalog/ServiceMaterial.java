package kleinreparatur_service.servicecatalog;

import kleinreparatur_service.materials.Material;

public class ServiceMaterial extends Material {

	private int quantity;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}