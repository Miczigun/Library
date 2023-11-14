package pl.polsl.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Book {
    @Id
    private long isbn;
    private String title;
    private String author;
    private String category;
    private long quantity;
}
