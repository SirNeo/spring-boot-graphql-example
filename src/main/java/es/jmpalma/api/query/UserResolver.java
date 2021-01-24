package es.jmpalma.api.query;

import java.util.Collection;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

import es.jmpalma.api.model.User;
import es.jmpalma.api.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserResolver implements GraphQLResolver<User> {
    
	private final UserService service;

    @PreAuthorize("isAuthenticated()")
    public String getToken(User user) {
        return service.getToken(user);
    }
    
    public Collection<String> getRoles(User user) {
        return user.getRoles();
    }
    
    public String getEmail(User user) {
        return service.isAuthenticated() && user != null ? user.getEmail() : null;
    }
    
}
