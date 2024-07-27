package kleinreparatur_service.order;

import kleinreparatur_service.item.Item;
import kleinreparatur_service.item.ItemService;
import org.javamoney.moneta.Money;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.order.OrderStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing custom orders.
 */
@Service
public class CustomOrderService {

	private final LocalDate currentDate = LocalDate.now();
	private final OrderManagement<CustomOrder> orderManagement;

	/**
	 * Constructor for CustomOrderService.
	 * @param orderManagement The order management system.
	 * @param itemService The service for managing items.
	 */
	public CustomOrderService(OrderManagement<CustomOrder> orderManagement, ItemService itemService) {
		this.orderManagement = orderManagement;
	}

	/**
	 * Calculates the number of days since the order was placed.
	 * @param order The order to check.
	 * @return The number of days since the order was placed.
	 */
	public long getDaysSinceOrder(CustomOrder order) {
		return ChronoUnit.DAYS.between(order.getOrderDate(), order.getOrderDate());
	}

	/**
	 * Calculates the number of days between order placement and completion.
	 * @param order The order to check.
	 * @return The number of days between order placement and completion.
	 */
	public long getDaysBetweenCompletion(CustomOrder order) {
		return ChronoUnit.DAYS.between(order.getCompletionDate(), order.getOrderDate());
	}

	/**
	 * Calculates the number of weeks since the order was completed.
	 * @param order The order to check.
	 * @return The number of weeks since the order was completed.
	 */
	public long getWeeksSinceCompletion(CustomOrder order) {
		return ChronoUnit.WEEKS.between(order.getCompletionDate(), currentDate);
	}

	/**
	 * Calculates and sets the refund amount for an order.
	 * We will return 10% each day after 7 days we are not complete with the service
	 * @param order The order to calculate the refund for.
	 */
	public void getRefundAmount(CustomOrder order) {
		long daysSinceOrder = getDaysSinceOrder(order);
		if (daysSinceOrder >= 7 && order.getStatus() == CustomOrder.OrderStatus.IN_WORKING) {
			double refundPercentage = Math.min(1.0, (daysSinceOrder - 6) * 0.10);
			order.setRefundAmount(Money.of(order.getCustomTotal().getNumber().doubleValue() * refundPercentage, "EUR"));
		} else {
			order.setRefundAmount(Money.of(0, "EUR"));
		}
	}

	/**
	 * Calculates and sets the storage fee for an order.
	 * @param order The order to calculate the storage fee for.
	 */
	public void getStorageFee(CustomOrder order) {
		if (order.getStatus() == CustomOrder.OrderStatus.IN_WORKING) {
			order.setStorageFee(Money.of(0, "EUR"));
		}
		long weeksSinceCompletion = getWeeksSinceCompletion(order);
		long daysSinceOrder = getDaysSinceOrder(order);
		if (daysSinceOrder >= 14 && weeksSinceCompletion >= 1
			&& order.getStatus() != CustomOrder.OrderStatus.ABGEHOLT) {
			order.setStorageFee(Money.of(1.5 * (weeksSinceCompletion), "EUR"));
		} else {
			order.setStorageFee(Money.of(0, "EUR"));
		}
	}

	/**
	 * Scheduled task to check and donate unclaimed items.
	 * Runs daily at midnight.
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void checkAndDonateUnclaimedItems() {
		LocalDate currentDate = LocalDate.now();
		LocalDate threeMonthsAgo = currentDate.minusMonths(3);

		List<CustomOrder> completedOrders = getCompletedOrders();

		for (CustomOrder order : completedOrders) {
			if (shouldDonateOrder(order, threeMonthsAgo)) {
				donateOrder(order);
			}
		}
	}

	/**
	 * Checks if an order should be donated.
	 * @param order The order to check.
	 * @param threeMonthsAgo The date three months ago.
	 * @return True if the order should be donated, false otherwise.
	 */
	public boolean shouldDonateOrder(CustomOrder order, LocalDate threeMonthsAgo) {
		return order.getCompletionDate() != null
			&& order.getCompletionDate().isBefore(threeMonthsAgo)
			&& order.getStatus() != CustomOrder.OrderStatus.ABGEHOLT
			&& order.getStatus() != CustomOrder.OrderStatus.TO_BE_DONATED
			&& order.getStatus() == CustomOrder.OrderStatus.COMPLETED;
	}

	/**
	 * Marks an order as to be donated and updates its status.
	 * @param order The order to donate.
	 */
	private void donateOrder(CustomOrder order) {
		order.setStatus(CustomOrder.OrderStatus.TO_BE_DONATED);
		orderManagement.save(order);
		order.getItem().setStatus(3);
	}

	/**
	 * Retrieves all the placed orders.
	 * @return A list of completed custom orders.
	 */
	public List<CustomOrder> getCompletedOrders() {
		return orderManagement.findBy(OrderStatus.COMPLETED)
			.stream()
			.map(CustomOrder.class::cast)
			.collect(Collectors.toList());
	}
}