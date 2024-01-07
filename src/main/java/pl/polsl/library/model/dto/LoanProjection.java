package pl.polsl.library.model.dto;

import java.time.LocalDate;

public interface LoanProjection {
    long getId();

    LocalDate getCheckOutDate();

    LocalDate getDueDate();

    LocalDate getReturnDate();

    boolean isReturnStatus();

    String getTitle();

    String getBookId();

}
