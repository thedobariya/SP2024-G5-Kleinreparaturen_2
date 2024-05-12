package servicecatalog;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Service {

	@Id
	private Integer serviceID;
	private Integer price;

	/* Getter und Setter für "serviceID" und "price" */
	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}

	public int getServiceID() {
		return serviceID;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}


}
