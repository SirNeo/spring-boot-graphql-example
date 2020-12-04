package es.jmpalma.api.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import es.jmpalma.api.enums.PersonOrderBy;
import es.jmpalma.api.filter.PersonFilter;
import es.jmpalma.api.model.Info;
import es.jmpalma.api.model.Person;
import es.jmpalma.api.model.Persons;
import es.jmpalma.api.repository.PersonRepository;
import es.jmpalma.api.utils.Constants;

@Component
public class PersonQuery implements GraphQLQueryResolver {

	@Autowired
	private PersonRepository repo;
	
	public Optional<Person> person(Integer id) {
        return repo.findById(id);
    }
	
	public Persons persons(Integer page, Integer size, PersonFilter filter, List<PersonOrderBy> orderBy) {
    	
		Persons tempNotes = new Persons();
    	Page<Person> pagePersons = null;
    	Info info = new Info();

    	if (page != null) {
    		if (page > 0) {
    			page -= 1;
    		}
    	} else {
    		page = 0;
    	}

    	if (size == null) {
    		size = Constants.MAX_RECORDS;
    	}
    	
    	// Ignore null values and case
    	ExampleMatcher ignoreNullMatcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase();
    	
    	List<Order> orderList = new ArrayList<Sort.Order>();
    	for (PersonOrderBy homeOrder : orderBy) {
			orderList.add(homeOrder.getOrder());
		}
    	
    	// Order by ID (default)
    	Pageable paging = PageRequest.of(page, size, Sort.by(orderList));
    	
    	if (filter != null ) {	
    		Person person = new Person();
    		person.setFirstName(filter.getFirstName());
    		person.setMiddleName(filter.getMiddleName());
    		person.setLastName(filter.getLastName());
    		person.setAge(filter.getAge());
        	
        	Example<Person> exPerson = Example.of(person, ignoreNullMatcher);
        	
        	pagePersons = repo.findAll(exPerson, paging);
    	} else {
    		pagePersons = repo.findAll(paging);
    	}
    	
    	info.setCount(pagePersons.getNumberOfElements());
    	info.setPages(pagePersons.getTotalPages());
    	info.setSize(size);
    	info.setTotal(pagePersons.getTotalElements());

    	tempNotes.setInfo(info);
    	tempNotes.setResults(pagePersons.getContent());

    	return tempNotes;
    }
}
