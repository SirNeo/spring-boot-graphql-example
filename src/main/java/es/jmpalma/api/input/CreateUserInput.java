package es.jmpalma.api.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserInput {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
