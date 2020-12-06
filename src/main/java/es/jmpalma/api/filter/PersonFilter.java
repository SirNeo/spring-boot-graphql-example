package es.jmpalma.api.filter;

import es.jmpalma.api.model.Person;
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
	
	Person convert() {
        return convert(this);
    }

    public static Person convert(PersonFilter person) {
        return person != null ? new Person(null, person.firstName, person.middleName, person.lastName, person.age) : null;
    }

}
