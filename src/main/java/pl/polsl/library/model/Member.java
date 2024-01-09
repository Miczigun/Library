package pl.polsl.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@code Member} entity represents a library member and implements the Spring Security UserDetails interface.
 */
@Entity
@Getter
@Setter
public class Member implements UserDetails {

    /**
     * The unique identifier for the member.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The unique email address associated with the member.
     */
    @Column(unique = true)
    private String email;

    /**
     * The password associated with the member.
     */
    private String password;

    /**
     * The first name of the member.
     */
    private String name;

    /**
     * The last name of the member.
     */
    private String surname;

    /**
     * The address of the member.
     */
    private String address;

    /**
     * The penalty payment status of the member.
     */
    private int penaltyPayment;

    /**
     * The set of roles (authorities) assigned to the member.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_junction",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> authorities;

    /**
     * Default constructor for the Member entity.
     */
    public Member() {
        super();
        authorities = new HashSet<>();
    }

    /**
     * Constructor for creating a member with basic information and roles.
     *
     * @param email       The email address of the member.
     * @param password    The password associated with the member.
     * @param authorities The set of roles assigned to the member.
     */
    public Member(String email, String password, Set<Role> authorities) {
        super();
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Constructor for creating a member with detailed information, roles, and address.
     *
     * @param email          The email address of the member.
     * @param password       The password associated with the member.
     * @param name           The first name of the member.
     * @param surname        The last name of the member.
     * @param address        The address of the member.
     * @param authorities    The set of roles assigned to the member.
     */
    public Member(String email, String password, String name, String surname, String address, Set<Role> authorities) {
        super();
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
