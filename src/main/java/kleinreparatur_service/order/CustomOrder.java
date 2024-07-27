package kleinreparatur_service.order;

import jakarta.persistence.*;
import kleinreparatur_service.customer.Customer;
import kleinreparatur_service.item.Item;
import kleinreparatur_service.resources.coworkers.Coworker;
import kleinreparatur_service.resources.workingstation.Workingstation;
import org.javamoney.moneta.Money;
import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.PaymentMethod;
import org.salespointframework.useraccount.UserAccount;

import java.time.LocalDate;

@Entity
public class CustomOrder extends Order {
	private LocalDate orderDate;
	private LocalDate completionDate;
	private LocalDate pickUpDate;
	private Money customTotal;
	private Money refundAmount;
	private Money storageFee;

	@ManyToOne
	private Customer customer;

	@ManyToOne
	private Item item;

	public enum OrderStatus {
		IN_WORKING,
		COMPLETED,
		ABGEHOLT,
		TO_BE_DONATED
	}

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@ManyToOne
	@JoinColumn(name = "workingstation_id")
	private Workingstation workingstation;

	@ManyToOne
	private Coworker assignedCoworker;

	public CustomOrder() {
	}

	public CustomOrder(UserAccount.UserAccountIdentifier userAccountIdentifier, PaymentMethod paymentMethod) {
		super(userAccountIdentifier, paymentMethod);
		this.orderDate = LocalDate.now();
		this.completionDate = this.orderDate.plusDays(7);
		this.pickUpDate = this.completionDate.plusDays(7);
		this.status = OrderStatus.IN_WORKING; // Default status
		this.customTotal = Money.of(0, "EUR"); // Initialize with zero
		this.refundAmount = Money.of(0, "EUR");
		this.storageFee = Money.of(0, "EUR");
	}

	public void updateTotal() {
		int itemCondition = this.item.getCondition();
		switch (itemCondition) {
			case 2:
				this.customTotal = this.customTotal.multiply(2);
				break;
			case 3:
				this.customTotal = this.customTotal.multiply(3);
				break;
			default:
				this.customTotal = this.customTotal.multiply(1);
				break;
		}
	}

	public void calculateTotalFromCart(Cart cart) {
		this.customTotal = Money.of(cart.getPrice().getNumber().doubleValueExact(), cart.getPrice().getCurrency());
		updateTotal(); // Apply condition-based modification
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

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public LocalDate getCompletionDate() {
		return completionDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public void setCompletionDate(LocalDate completionDate) {
		this.completionDate = completionDate;
	}

	public LocalDate getPickUpDate() {
		return pickUpDate;
	}

	public void setPickUpDate(LocalDate pickupDate) {
		this.pickUpDate = pickupDate;
	}

	public Money getCustomTotal() {
		return customTotal;
	}

	public void setCustomTotal(Money customTotal) {
		this.customTotal = customTotal;
	}

	public Money getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Money refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Money getStorageFee() {
		return storageFee;
	}

	public void setStorageFee(Money storageFee) {
		this.storageFee = storageFee;
	}

	public Workingstation getWorkingstation() {
		return workingstation;
	}

	public void setWorkingstation(Workingstation workingstation) {
		this.workingstation = workingstation;
	}

	public Coworker getAssignedCoworker() {
		return assignedCoworker;
	}

	public void removeAssignedCoworker() {
		this.assignedCoworker = null;
	}

	public void setAssignedCoworker(Coworker assignedCoworker) {
		this.assignedCoworker = assignedCoworker;
	}
}