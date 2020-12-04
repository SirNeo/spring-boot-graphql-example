package es.jmpalma.api.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import es.jmpalma.api.model.Person;
import es.jmpalma.api.repository.PersonRepository;

@Component
public class PersonMutation implements GraphQLMutationResolver  {
 
    @Autowired
    private PersonRepository repo;
 
    public Person newPerson(String firstName, String middleName, String lastName, Integer age) {
    	
    	Person person = new Person();
    	
    	person.setFirstName(firstName);
    	person.setMiddleName(middleName);
    	person.setLastName(lastName);
    	person.setAge(age);
    	
    	person = repo.save(person);
    	
        return person;
    }

}