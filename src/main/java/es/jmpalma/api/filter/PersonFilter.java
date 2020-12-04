package es.jmpalma.api.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PersonFilter {

	private String firstName;

	private String middleName;

	private String lastName;

	private Integer age;

}
