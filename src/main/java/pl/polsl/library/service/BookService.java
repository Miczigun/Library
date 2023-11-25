package pl.polsl.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.library.model.Book;
import pl.polsl.library.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public Book getSingleBook(long id){
        return bookRepository.findById(id).orElseThrow();
    }

    public Book addBook(Book book){
        return bookRepository.save(book);
    }

    public void deleteBook(long id){
        bookRepository.deleteById(id);
    }

}
