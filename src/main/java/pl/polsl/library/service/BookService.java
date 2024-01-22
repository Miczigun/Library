package pl.polsl.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.polsl.library.model.Book;
import pl.polsl.library.repository.BookRepository;

import java.util.List;

/**
 * The {@code BookService} class provides services related to Book entities, including CRUD operations
 * and retrieving books with optional pagination and filtering by title.
 */
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final static int PAGE_SIZE = 10;


    /**
     * Get a list of books with optional pagination and title filtering.
     *
     * @param page  The page number (starting from 0).
     * @param title The title for filtering books (can be empty for all books).
     * @return List of books based on the specified page and title filter.
     */
    public List<Book> getBooks(int page, String title){
        return bookRepository.findAllBooks(title, PageRequest.of(page,PAGE_SIZE));
    }

    /**
     * Get a single book by its ID.
     *
     * @param id The ID of the book.
     * @return The book with the specified ID.
     */
    public Book getSingleBook(long id){
        return bookRepository.findById(id).orElseThrow();
    }

    /**
     * Add a new book to the library.
     *
     * @param book The book to be added.
     * @return The added book.
     */
    public Book addBook(Book book){
        return bookRepository.save(book);
    }

    /**
     * Delete a book by its ID.
     *
     * @param id The ID of the book to be deleted.
     */
    public void deleteBook(long id){
        bookRepository.deleteById(id);
    }

}
