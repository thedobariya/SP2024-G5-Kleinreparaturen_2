package kleinreparatur_service.users;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import kleinreparatur_service.users.user.UserIdentifier;

import java.io.Serializable;
import java.util.UUID;

import org.jmolecules.ddd.types.Identifier;
import org.salespointframework.core.AbstractAggregateRoot;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class user extends AbstractAggregateRoot<UserIdentifier> {

	private @EmbeddedId UserIdentifier id = new UserIdentifier();

	private String address;

	@OneToOne
	private UserAccount userAccount;

	@SuppressWarnings("unused")
	private user() {}

	public user(UserAccount userAccount, String address) {
		this.userAccount = userAccount;
		this.address = address;
	}

	@Override
	public UserIdentifier getId() {	return id;	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	@Embeddable
	public static final class UserIdentifier implements Identifier, Serializable {

		private static final long serialVersionUID = 7740660930809051850L;
		private final UUID identifier;

		UserIdentifier() {
			this(UUID.randomUUID());
		}

		UserIdentifier(UUID identifier) {
			this.identifier = identifier;
		}

		@Override
		public int hashCode() {

			final int prime = 31;
			int result = 1;

			result = prime * result + (identifier == null ? 0 : identifier.hashCode());

			return result;
		}

		@Override
		public boolean equals(Object obj) {

			if (obj == this) {
				return true;
			}

			if (!(obj instanceof UserIdentifier that)) {
				return false;
			}

			return this.identifier.equals(that.identifier);
		}
	}
}