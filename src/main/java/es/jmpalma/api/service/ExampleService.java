package es.jmpalma.api.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class ExampleService<T> {
	
	ExampleMatcher ignoreNullMatcher;
	
	ExampleMatcher containsMatcher;
	
	public ExampleService() {
		this.ignoreNullMatcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase();
		this.containsMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase();
	}

	public Example<T> getExampleIgnore(T entity){
		return Example.of(entity, this.ignoreNullMatcher);
	}
	
	public Example<T> getExampleContains(T entity){
		return Example.of(entity, this.containsMatcher);
	}

}
