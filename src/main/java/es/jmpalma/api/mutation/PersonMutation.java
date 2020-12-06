package es.jmpalma.api.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import es.jmpalma.api.model.Person;
import es.jmpalma.api.service.PersonService;

@Component
public class PersonMutation implements GraphQLMutationResolver  {
 
	@Autowired
	private PersonService service;
 
    public Person newPerson(String firstName, String middleName, String lastName, Integer age) {
    	
    	return service.save(Person.builder()
        		.firstName(firstName)
        		.middleName(middleName)
        		.lastName(lastName)
        		.age(age)
        		.build());
    	
    }

}