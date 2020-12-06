package es.jmpalma.api.service;

import java.util.List;
import java.util.Optional;

import es.jmpalma.api.enums.PersonOrderBy;
import es.jmpalma.api.model.Person;
import es.jmpalma.api.model.Persons;

public interface PersonService {

    Persons find(Person person, Integer page, Integer size, List<PersonOrderBy> orderBy);

    Optional<Person> findById(Integer id);

    Person save(Person person);
    
}