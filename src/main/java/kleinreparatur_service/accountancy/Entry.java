package kleinreparatur_service.accountancy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.javamoney.moneta.Money;

import java.time.LocalDate;

@Entity
public class Entry {

	@Id
	@GeneratedValue
	private Long id;

	private String description;
	private double amount;
	private LocalDate date;

	// Constructors
	public Entry() {}

	public Entry(String description, double amount) {
		this.description = description;
		this.amount = amount;
		this.date = LocalDate.now();
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}