package es.jmpalma.api.query;

import java.util.List;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import es.jmpalma.api.enums.UserOrderBy;
import es.jmpalma.api.filter.UserFilter;
import es.jmpalma.api.model.User;
import es.jmpalma.api.model.Users;
import es.jmpalma.api.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserQuery implements GraphQLQueryResolver {
    
	private final UserService service;
    
    public User getCurrentUser() {
        return service.getCurrentUser();
    }
    
    public long getUsersCount() {
        return service.countAll();
    }
    
    public User getUser(Long id) {
    	return service.getById(id);
    }
    
    public Users getUsers(Integer page, Integer size, UserFilter filter, List<UserOrderBy> orderByList) {
    	return service.find(UserFilter.convert(filter), page, size, orderByList);
    }
   
}
