package pl.polsl.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.polsl.library.model.Member;
import pl.polsl.library.model.dto.MemberProjection;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    @Query("SELECT m.id as id, m.email AS email, m.name AS name, m.surname AS surname, m.address AS address FROM Member m")
    List<MemberProjection> findAllMembers();

    @Query("SELECT m.id as id, m.email AS email, m.name AS name, m.surname AS surname, m.address AS address FROM Member m WHERE m.id = :id")
    MemberProjection findMemberById(@Param("id") long id);
}