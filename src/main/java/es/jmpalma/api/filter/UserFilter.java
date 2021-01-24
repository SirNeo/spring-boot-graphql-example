package es.jmpalma.api.filter;

import es.jmpalma.api.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserFilter {

	private String firstName;

	private String lastName;

	private String email;
	
	private String role;
	
	User convert() {
        return convert(this);
    }

    public static User convert(UserFilter filter) {
        return filter != null ? new User(null, filter.firstName, filter.lastName, filter.email, null, null) : null;
    }

}
