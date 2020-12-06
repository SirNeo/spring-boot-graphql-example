package es.jmpalma.api.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import es.jmpalma.api.enums.PersonOrderBy;
import es.jmpalma.api.utils.Constants;

public class InfoService {

	public static Pageable getPageable(Integer page, Integer size, List<PersonOrderBy> orderBy) {
    	return PageRequest.of(page==null?0:page>0?page-=1:page, size==null?Constants.MAX_RECORDS:size, Sort.by(PersonOrderBy.getOrderByList(orderBy)));
	}
	
}
