package es.jmpalma.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.jmpalma.api.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}
