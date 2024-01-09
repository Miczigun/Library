package pl.polsl.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.library.model.Role;

import java.util.Optional;

/**
 * The {@code RoleRepository} interface provides CRUD operations for {@link Role} entities.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Retrieve a role by its authority.
     *
     * @param authority The authority of the role.
     * @return An optional containing the role with the specified authority, or empty if not found.
     */
    Optional<Role> findByAuthority(String authority);
}