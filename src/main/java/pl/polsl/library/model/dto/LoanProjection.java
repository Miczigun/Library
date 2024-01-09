package pl.polsl.library.model.dto;

import java.time.LocalDate;

/**
 * The {@code LoanProjection} interface represents a projection of loan data with selected attributes.
 * It provides a subset of loan information for simplified use cases.
 */
public interface LoanProjection {

    /**
     * Get the unique identifier for the loan.
     *
     * @return The unique identifier for the loan.
     */
    long getId();

    /**
     * Get the date when the book is checked out for the loan.
     *
     * @return The check-out date of the loan.
     */
    LocalDate getCheckOutDate();

    /**
     * Get the due date for the return of the book.
     *
     * @return The due date of the loan.
     */
    LocalDate getDueDate();

    /**
     * Get the date when the book is returned.
     *
     * @return The return date of the loan.
     */
    LocalDate getReturnDate();

    /**
     * Get the return status indicating whether the book has been returned.
     *
     * @return The return status of the loan.
     */
    boolean isReturnStatus();

    /**
     * Get the title of the associated book.
     *
     * @return The title of the book.
     */
    String getTitle();

    /**
     * Get the unique identifier of the associated book.
     *
     * @return The unique identifier of the book.
     */
    String getBookId();
}
