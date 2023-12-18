package pl.polsl.library.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class LoanDto {
    private long id;
    private long bookId;
    private String title;
    private LocalDate checkOutDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean returnStatus;

    public LoanDto(long id, long bookId, String bookTitle, LocalDate checkOutDate, LocalDate dueDate, LocalDate returnDate, boolean returnStatus) {
        this.id = id;
        this.bookId = bookId;
        this.title = bookTitle;
        this.checkOutDate = checkOutDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.returnStatus = returnStatus;
    }
}
