package pl.polsl.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.polsl.library.model.Loan;
import pl.polsl.library.model.dto.LoanProjection;

import java.util.List;

/**
 * The {@code LoanRepository} interface provides CRUD operations for {@link Loan} entities
 * and custom queries for retrieving loans and projections.
 */
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    /**
     * Retrieve a list of loan projections for a specific member ID.
     *
     * @param memberId The ID of the member.
     * @return List of {@link LoanProjection} containing essential details about the member's loans.
     */
    @Query("SELECT l.id as id, b.id as bookId, b.title as title, l.checkOutDate as checkOutDate, l.dueDate as dueDate, l.returnDate as returnDate, l.returnStatus as returnStatus" +
            " FROM Loan l JOIN l.bookId b WHERE l.memberId.id = :memberId")
    List<LoanProjection> findMemberLoans(@Param("memberId") Long memberId);

    /**
     * Retrieve a list of active loan IDs for a specific member ID.
     *
     * @param memberId The ID of the member.
     * @return List of active loan IDs for the specified member ID.
     */
    @Query("SELECT l.id as id FROM Loan l WHERE l.memberId.id = :memberId AND l.returnStatus = false")
    List<Long> findActiveLoansByMemberId(@Param("memberId") Long memberId);
}