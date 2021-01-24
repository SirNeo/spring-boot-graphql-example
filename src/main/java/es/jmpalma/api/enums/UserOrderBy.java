package es.jmpalma.api.enums;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import lombok.Getter;

public enum UserOrderBy {

	ID_ASC(Sort.Direction.ASC, "id"),
	ID_DESC(Sort.Direction.DESC, "id"),
	FIRST_NAME_ASC(Sort.Direction.ASC, "firstName"),
	FIRST_NAME_DESC(Sort.Direction.DESC, "firstName"),
	LAST_NAME_ASC(Sort.Direction.ASC, "lastName"),
	LAST_NAME_DESC(Sort.Direction.DESC, "lastName"),
	EMAIL_ASC(Sort.Direction.ASC, "email"),
	EMAIL_DESC(Sort.Direction.DESC, "email");
	
	@Getter
	private Order order = null;

	UserOrderBy(Direction desc, String string) {
		order = new Order(desc, string);
	}
	
	public static List<Order> getOrderByList(List<UserOrderBy> orderBy) {
		List<Order> orderList = new ArrayList<Sort.Order>();
    	for (UserOrderBy ord : orderBy) {
			orderList.add(ord.getOrder());
		}
    	return orderList;
	}
}
