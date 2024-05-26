package kickstart.service;

import jakarta.persistence.Entity;
import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;

import javax.money.MonetaryAmount;

@Entity
public class Service extends Product {
	public static enum ServiceType{
		PATCHWORK,
		CLEANING,
		SEWING,
		LOCKSMITH,
		SCISSOR,
		ELECTRICAL;
	}

	private String name;
	private ServiceType type;

	public Service() {}

	public Service(String name, Money price, String name1, ServiceType type) {
		super(name, price);
		this.name = name1;
		this.type = type;
	}

	@Override
	public String getName() {
		return name;
	}

	public ServiceType getType() {
		return type;
	}
}
