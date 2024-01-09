package pl.polsl.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.polsl.library.model.Member;
import pl.polsl.library.model.dto.MemberProjection;

import java.util.List;
import java.util.Optional;

/**
 * The {@code MemberRepository} interface provides CRUD operations for {@link Member} entities
 * and custom queries for retrieving members and projections.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * Retrieve a member by their email.
     *
     * @param email The email of the member.
     * @return An optional containing the member with the specified email, or empty if not found.
     */
    Optional<Member> findByEmail(String email);

    /**
     * Retrieve a list of members with basic information.
     *
     * @return List of {@link MemberProjection} containing essential details about library members.
     */
    @Query("SELECT m.id as id, m.email AS email, m.name AS name, m.surname AS surname, m.address AS address FROM Member m")
    List<MemberProjection> findAllMembers();

    /**
     * Retrieve a member projection by their ID.
     *
     * @param id The ID of the member.
     * @return The {@link MemberProjection} with the specified ID.
     */
    @Query("SELECT m.id as id, m.email AS email, m.name AS name, m.surname AS surname, m.address AS address FROM Member m WHERE m.id = :id")
    MemberProjection findMemberById(@Param("id") long id);
}