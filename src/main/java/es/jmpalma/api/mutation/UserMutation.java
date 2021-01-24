package es.jmpalma.api.mutation;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import es.jmpalma.api.exceptions.BadCredentialsException;
import es.jmpalma.api.input.CreateUserInput;
import es.jmpalma.api.input.UpdatePasswordInput;
import es.jmpalma.api.input.UpdateUserInput;
import es.jmpalma.api.model.User;
import es.jmpalma.api.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMutation implements GraphQLMutationResolver {

	private final UserService service;
	private final AuthenticationProvider authenticationProvider;
	
	@PreAuthorize("isAuthenticated()")
	public User createUser(CreateUserInput input) {
        return service.createUser(input);
    }
	
	@PreAuthorize("isAuthenticated()")
    public User updatePassword(UpdatePasswordInput input) {
        return service.updatePassword(service.getCurrentUser().getId(), input);
    }

    @PreAuthorize("isAuthenticated()")
    public User updateUser(UpdateUserInput input) {
        return service.update(service.getCurrentUser().getId(), input);
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteUser(long id) {
        return service.deleteUser(id);
    }
    
    @PreAuthorize("isAnonymous()")
    public User login(String email, String password) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(email, password);
        try {
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(credentials));
            return service.getCurrentUser();
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException(email);
        }
    }
    
}
