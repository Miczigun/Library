package pl.polsl.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * The {@code Book} entity represents a book in the library.
 */
@Entity
@Setter
@Getter
public class Book {

    /**
     * The unique identifier for the book.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The International Standard Book Number (ISBN) of the book.
     */
    private long isbn;

    /**
     * The title of the book.
     */
    private String title;

    /**
     * The author(s) of the book.
     */
    private String author;

    /**
     * The category or genre of the book.
     */
    private String category;

    /**
     * The quantity of available copies of the book in the library.
     */
    private long quantity;

    /**
     * The availability status of the book.
     */
    private boolean availabilityStatus;
}

