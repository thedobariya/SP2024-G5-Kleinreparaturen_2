package kleinreparatur_service.finance;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Finance {

	@Id
	private Integer yearMonth;
	private Integer value;

	/* Getter und Setter für "yearMonth" und "value" */
	public void setYearMonth(int yearMonth) {
		this.yearMonth = yearMonth;
	}

	public int getYearMonth() {
		return yearMonth;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}