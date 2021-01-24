package es.jmpalma.api.service;

import static es.jmpalma.api.utils.StreamUtils.collectionStream;
import static java.util.function.Predicate.not;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import es.jmpalma.api.enums.UserOrderBy;
import es.jmpalma.api.exceptions.BadCredentialsException;
import es.jmpalma.api.exceptions.BadTokenException;
import es.jmpalma.api.exceptions.UserAlreadyExistsException;
import es.jmpalma.api.exceptions.UserNotFoundException;
import es.jmpalma.api.input.CreateUserInput;
import es.jmpalma.api.input.UpdatePasswordInput;
import es.jmpalma.api.input.UpdateUserInput;
import es.jmpalma.api.model.Info;
import es.jmpalma.api.model.User;
import es.jmpalma.api.model.Users;
import es.jmpalma.api.repository.UserRepository;
import es.jmpalma.api.security.JWTUserDetails;
import es.jmpalma.api.security.SecurityProperties;
import es.jmpalma.api.utils.Constants;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	
    private static final String ADMIN_AUTHORITY = "ADMIN";
    private static final String USER_AUTHORITY = "USER";
    private final UserRepository repository;
    private final SecurityProperties properties;
    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final PasswordEncoder passwordEncoder;
    
    public Users find(User user, Integer page, Integer size, List<UserOrderBy> orderBy) {
    	
    	PageRequest pageable = PageRequest.of(page==null?0:page>0?page-=1:page, size==null?Constants.MAX_RECORDS:size, Sort.by(UserOrderBy.getOrderByList(orderBy)));
    	
    	Page<User> pageUsers = 
    			(user==null)?
    				repository.findAll(pageable):
    					repository.findAll(new ExampleService<User>().getExampleContains(user),  pageable);	
            
        	return Users.builder()
        			.info(new Info(pageUsers))
        			.results(pageUsers.getContent())
        			.build();
    }
    
    public long countAll() {
        return repository.count();
    }

    public User getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    @Transactional
    public JWTUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository
            .findByEmail(email)
            .map(user -> getUserDetails(user, getToken(user)))
            .orElseThrow(() -> new UsernameNotFoundException("Username or password didn''t match"));
    }

    @Transactional
    public JWTUserDetails loadUserByToken(String token) {
        return getDecodedToken(token)
            .map(DecodedJWT::getSubject)
            .flatMap(repository::findByEmail)
            .map(user -> getUserDetails(user, token))
            .orElseThrow(BadTokenException::new);
    }

    @Transactional
    public User getCurrentUser() {
        return Optional
            .ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getName)
            .flatMap(repository::findByEmail)
            .orElse(null);
    }

    @Transactional
    public User createUser(CreateUserInput input) {
        if (!exists(input)) {
            return repository.saveAndFlush(User
                .builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .roles(Set.of(USER_AUTHORITY))
                .build());
        } else {
            throw new UserAlreadyExistsException(input.getEmail());
        }
    }
    
    @Transactional
    public User update(Long id, UpdateUserInput input) {
        User user = getById(id);
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        return user;
    }

    @Transactional
    public User updatePassword(Long personId, UpdatePasswordInput input) {
        User user = repository.findById(personId).orElseThrow(() -> new UserNotFoundException(personId));
        if (passwordEncoder.matches(input.getOriginalPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(input.getNewPassword()));
        } else {
            throw new BadCredentialsException(user.getEmail());
        }
        return user;
    }

    @Transactional
    public String getToken(User user) {
        Instant now = Instant.now();
        Instant expiry = Instant.now().plus(properties.getTokenExpiration());
        return JWT
            .create()
            .withIssuer(properties.getTokenIssuer())
            .withIssuedAt(Date.from(now))
            .withExpiresAt(Date.from(expiry))
            .withSubject(user.getEmail())
            .sign(algorithm);
    }

    public boolean isAdmin() {
        return Optional
            .ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getAuthorities)
            .stream()
            .flatMap(Collection::stream)
            .map(GrantedAuthority::getAuthority)
            .anyMatch(ADMIN_AUTHORITY::equals);
    }

    public boolean isAuthenticated() {
        return Optional
            .ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .filter(not(this::isAnonymous))
            .isPresent();
    }

    private boolean isAnonymous(Authentication authentication) {
        return authentication instanceof AnonymousAuthenticationToken;
    }

    @Transactional
    public boolean deleteUser(Long personId) {
        if (repository.existsById(personId)) {
            repository.deleteById(personId);
            return true;
        } else {
            return false;
        }
    }

    private boolean exists(CreateUserInput input) {
        return repository.existsByEmail(input.getEmail());
    }

    private JWTUserDetails getUserDetails(User user, String token) {
        return JWTUserDetails
            .builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .authorities(collectionStream(user.getRoles())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()))
            .token(token)
            .build();
    }

    private Optional<DecodedJWT> getDecodedToken(String token) {
        try {
            return Optional.of(verifier.verify(token));
        } catch(JWTVerificationException ex) {
            return Optional.empty();
        }
    }
}
