package pl.polsl.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.library.model.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

}