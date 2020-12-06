package es.jmpalma.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jmpalma.api.enums.PersonOrderBy;
import es.jmpalma.api.model.Info;
import es.jmpalma.api.model.Person;
import es.jmpalma.api.model.Persons;
import es.jmpalma.api.repository.PersonRepository;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	private PersonRepository repo;

	@Override
	public Persons find(Person person, Integer page, Integer size, List<PersonOrderBy> orderBy) {
    		
		Page<Person> pagePersons = 
			(person==null)?
				repo.findAll(InfoService.getPageable(page, size, orderBy)):
					repo.findAll(new ExampleService<Person>().getExampleContains(person), 
							InfoService.getPageable(page, size, orderBy));	
        
    	return Persons.builder()
    			.info(new Info(pagePersons))
    			.results(pagePersons.getContent())
    			.build();
	}

	@Override
	public Optional<Person> findById(Integer id) {
		return repo.findById(id);
	}

	@Override
	public Person save(Person person) {
		return repo.save(person);
	}
}
