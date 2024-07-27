package kleinreparatur_service.item;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Item {

	@Id
	private Long id;

	@Pattern(regexp = "[A-Za-z\\s]*")
	private String name;

	private boolean isOpen;

	/**
	 * 1: in Bearbeitung
	 * 2: fertig bearbeitet
	 * 3: zum Spenden freigegeben
	 * 4: gespendet
	 */

	@Min(1)
	@Max(4)
	private int status;

	@Pattern(regexp = "[A-Za-z\\s]*")
	@Size(max = 50)
	private String description;

	@Min(1)
	@Max(3)
	private int condition;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	@Min(1)
	@Max(4)
	public int getStatus() {
		return status;
	}

	public void setStatus(@Min(1) @Max(4) int status) {
		this.status = status;
	}
}