package es.jmpalma.api.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import es.jmpalma.api.enums.PersonOrderBy;
import es.jmpalma.api.filter.PersonFilter;
import es.jmpalma.api.model.Person;
import es.jmpalma.api.model.Persons;
import es.jmpalma.api.service.PersonService;

@Component
public class PersonQuery implements GraphQLQueryResolver {

	@Autowired
	private PersonService service;
	
	public Optional<Person> person(Integer id) {
        return service.findById(id);
    }
	
	public Persons persons(Integer page, Integer size, PersonFilter filter, List<PersonOrderBy> orderBy) {
		return service.find(PersonFilter.convert(filter), page, size, orderBy);
    }
}
