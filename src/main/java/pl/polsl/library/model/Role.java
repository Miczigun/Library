package pl.polsl.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * The {@code Role} entity represents a user role and implements the Spring Security GrantedAuthority interface.
 */
@Entity
@Getter
@Setter
public class Role implements GrantedAuthority {

    /**
     * The unique identifier for the role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The authority (name) of the role.
     */
    private String authority;

    /**
     * Default constructor for the Role entity.
     */
    public Role() {
        super();
    }

    /**
     * Constructor for creating a role with the specified authority.
     *
     * @param authority The authority (name) of the role.
     */
    public Role(String authority) {
        this.authority = authority;
    }

    /**
     * Constructor for creating a role with the specified ID and authority.
     *
     * @param roleId    The unique identifier for the role.
     * @param authority The authority (name) of the role.
     */
    public Role(long roleId, String authority) {
        this.id = roleId;
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
