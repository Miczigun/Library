package pl.polsl.library.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.polsl.library.model.Book;

import java.util.List;

/**
 * The {@code BookRepository} interface provides CRUD operations for {@link Book} entities
 * and a custom query for retrieving books with optional title filtering and pagination.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Retrieve a list of books with optional title filtering and pagination.
     *
     * @param title The title for filtering books (can be null for all books).
     * @param page  The page information for pagination.
     * @return List of books based on the specified title filter and pagination.
     */
    @Query("SELECT b FROM Book b WHERE :title IS NULL OR b.title LIKE %:title%")
    List<Book> findAllBooks(@Param("title") String title, Pageable page);
}