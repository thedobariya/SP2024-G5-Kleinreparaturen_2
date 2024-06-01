package kickstart.order;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import kickstart.customer.Customer;
import kickstart.item.Item;
import org.salespointframework.order.Order;
import org.salespointframework.useraccount.UserAccount;

;

@Entity
public class CustomOrder extends Order {

	@ManyToOne
	private Customer customer;

	@OneToOne
	private Item item;

//	public CustomOrder(UserAccount userAccount) {
//		super(userAccount);
//	}

	public CustomOrder() {

	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}
