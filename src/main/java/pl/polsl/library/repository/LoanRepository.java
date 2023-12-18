package pl.polsl.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.polsl.library.model.Loan;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByMemberId_Id(long id);

    @Query("SELECT l.id as id FROM Loan l WHERE l.memberId.id = :memberId AND l.returnStatus = false")
    List<Long> findActiveLoansByMemberId(@Param("memberId") Long memberId);
}