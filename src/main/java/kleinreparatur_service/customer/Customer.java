package kleinreparatur_service.customer;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Generate ID automatically by the database
	private Long id;

//	@NotNull
//	@Size(min = 2, max = 50)
	private String firstName;

//	@NotNull
//	@Size(min = 2, max = 50)
	private String lastName;

//	@NotNull
//	@Past
//	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

//	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<Item> items;

	// Constructors, getters, and setters
	public Customer(String firstName, String lastName, Date dateOfBirth, Object o) {}

	public Customer(String firstName, String lastName, Date dateOfBirth/*, List<Item> items*/) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
//		this.items = items;
//		if (items != null) {
//			for (Item item : items) {
//				item.setCustomer(this);
//			}
//		}
	}

	public Customer() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
