package es.jmpalma.api.enums;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import lombok.Getter;

public enum PersonOrderBy {

	ID_ASC(Sort.Direction.ASC, "id"),
	ID_DESC(Sort.Direction.DESC, "id"),
	FIRST_NAME_ASC(Sort.Direction.ASC, "firstName"),
	FIRST_NAME_DESC(Sort.Direction.DESC, "firstName"),
	MIDDLE_NAME_ASC(Sort.Direction.ASC, "middleName"),
	MIDDLE_NAME_DESC(Sort.Direction.DESC, "middleName"),
	LAST_NAME_ASC(Sort.Direction.ASC, "lastName"),
	LAST_NAME_DESC(Sort.Direction.DESC, "lastName"),
	AGE_ASC(Sort.Direction.ASC, "age"),
	AGE_DESC(Sort.Direction.DESC, "age");
	
	@Getter
	private Order order = null;

	PersonOrderBy(Direction desc, String string) {
		order = new Order(desc, string);
	}
	
	public static List<Order> getOrderByList(List<PersonOrderBy> orderBy) {
		List<Order> orderList = new ArrayList<Sort.Order>();
    	for (PersonOrderBy personOrder : orderBy) {
			orderList.add(personOrder.getOrder());
		}
    	return orderList;
	}
}
