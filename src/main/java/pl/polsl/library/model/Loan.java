package pl.polsl.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * The {@code Loan} entity represents a loan transaction between a member and a book in the library.
 */
@Entity
@Getter
@Setter
public class Loan {

    /**
     * The unique identifier for the loan.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The associated book for the loan.
     */
    @ManyToOne
    @JoinColumn(name = "id_book")
    private Book bookId;

    /**
     * The associated member for the loan.
     */
    @ManyToOne
    @JoinColumn(name = "id_member")
    private Member memberId;

    /**
     * The date when the book is checked out for the loan.
     */
    private LocalDate checkOutDate;

    /**
     * The due date for the return of the book.
     */
    private LocalDate dueDate;

    /**
     * The date when the book is returned.
     */
    private LocalDate returnDate;

    /**
     * The return status indicating whether the book has been returned.
     */
    private boolean returnStatus;

    /**
     * Default constructor for the Loan entity.
     */
    public Loan() {
    }

    /**
     * Parameterized constructor for creating a loan with a specified book and member.
     *
     * @param book   The book associated with the loan.
     * @param member The member associated with the loan.
     */
    public Loan(Book book, Member member) {
        this.bookId = book;
        this.memberId = member;
        this.checkOutDate = LocalDate.now();
        this.returnStatus = false;
    }
}
