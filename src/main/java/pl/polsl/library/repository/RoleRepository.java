package pl.polsl.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.library.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByAuthority(String authority);
}