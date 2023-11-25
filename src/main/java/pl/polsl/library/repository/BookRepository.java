package pl.polsl.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.library.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}