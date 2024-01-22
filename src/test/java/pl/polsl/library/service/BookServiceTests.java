package pl.polsl.library.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import pl.polsl.library.model.Book;
import pl.polsl.library.repository.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void getBooks_shouldReturnCorrectListOfBooks() {
        int page = 0;
        String title = "T";

        Book testBook1, testBook2;
        testBook1 = new Book();
        testBook2 = new Book();

        testBook1.setId(1);
        testBook1.setAuthor("Author 1");
        testBook1.setTitle("Title 1");
        testBook1.setQuantity(5);
        testBook1.setAvailabilityStatus(true);
        testBook1.setCategory("Category 1");
        testBook1.setIsbn(12345678);

        testBook2.setId(2);
        testBook2.setAuthor("Author 2");
        testBook2.setTitle("Title 2");
        testBook2.setQuantity(3);
        testBook2.setAvailabilityStatus(false);
        testBook2.setCategory("Category 3");
        testBook2.setIsbn(87654321);

        List<Book> expectedBooks = Arrays.asList(testBook1, testBook2);

        when(bookRepository.findAllBooks(title, PageRequest.of(page, 20)))
                .thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getBooks(page, title);

        assertThat(actualBooks).isNotNull();
        assertThat(actualBooks).hasSize(2);
        assertThat(actualBooks).containsExactlyElementsOf(expectedBooks);
    }

    @Test
    void getSingleBook_shouldReturnCorrectBook(){
        Book testBook1, testBook2;
        testBook1 = new Book();

        testBook1.setId(1);
        testBook1.setAuthor("Author 1");
        testBook1.setTitle("Title 1");
        testBook1.setQuantity(5);
        testBook1.setAvailabilityStatus(true);
        testBook1.setCategory("Category 1");
        testBook1.setIsbn(12345678);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook1));
        testBook2 = bookService.getSingleBook(1);

        assertThat(testBook2).isEqualTo(testBook1);
    }

    @Test
    void addBook_addsBookCorrectly(){
        Book testBook1, testBook2;
        testBook1 = new Book();

        testBook1.setId(1);
        testBook1.setAuthor("Author 1");
        testBook1.setTitle("Title 1");
        testBook1.setQuantity(5);
        testBook1.setAvailabilityStatus(true);
        testBook1.setCategory("Category 1");
        testBook1.setIsbn(12345678);

        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> {
            Book savedBook = invocation.getArgument(0);
            savedBook.setId(1L);
            return savedBook;
        });

        Book addedBook = bookService.addBook(testBook1);

        assertThat(addedBook).isNotNull();
        assertThat(addedBook.getId()).isNotNull();
        assertThat(addedBook.getId()).isEqualTo(testBook1.getId());
        assertThat(addedBook.getIsbn()).isEqualTo(testBook1.getIsbn());
        assertThat(addedBook.getTitle()).isEqualTo(testBook1.getTitle());
        assertThat(addedBook.getAuthor()).isEqualTo(testBook1.getAuthor());
        assertThat(addedBook.getQuantity()).isEqualTo(testBook1.getQuantity());
        assertThat(addedBook.getCategory()).isEqualTo(testBook1.getCategory());
        assertThat(addedBook.isAvailabilityStatus()).isEqualTo(testBook1.isAvailabilityStatus());

        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void deleteBookTest(){
        long bookIdToDelete = 1L;
        bookService.deleteBook(bookIdToDelete);
        verify(bookRepository).deleteById(bookIdToDelete);
    }


}
