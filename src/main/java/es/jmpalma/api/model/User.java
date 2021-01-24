package es.jmpalma.api.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
    
	@Column(name = "email")
    private String email;
    
	@Setter
    private String password;
		
	// private LocalDate created;
    
	@ElementCollection
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "name")
    private Set<String> roles;
    
    public User withRole(String role) {
        if (this.roles == null) {
            this.roles = Set.of(role);
        } else {
            this.roles.add(role);
        }
        return this;
    }

    public User withoutRole(String role) {
        if (this.roles != null) {
            this.roles.remove(role);
        }
        return this;
    }
}
