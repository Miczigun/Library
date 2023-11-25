package pl.polsl.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "id_book")
    private Book bookId;
    @ManyToOne
    @JoinColumn(name = "id_member")
    private Member memberId;
    private LocalDate CheckOutDate;
    private LocalDate DueDate;
    private LocalDate ReturnDate;
}
