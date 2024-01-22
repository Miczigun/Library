package pl.polsl.library.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.polsl.library.model.Book;
import pl.polsl.library.service.BookService;
import pl.polsl.library.service.LibrarianService;
import pl.polsl.library.service.MemberService;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LibrarianControllerTests {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(librarianController).build();
    }

    @Mock
    private LibrarianService librarianService;

    @Mock
    private BookService bookService;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private LibrarianController librarianController;

    private MockMvc mockMvc;



    @Test
    void testGetMemberBooks() throws Exception {
        long memberId = 1L;

        when(memberService.getUserLoans(anyLong())).thenReturn(anyList());

        mockMvc.perform(get("/librarian/user/{id}", memberId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(memberService, times(1)).getUserLoans(eq(memberId));
    }




    @Test
    void testGetBooks() throws Exception {
        int pageNumber = 0;
        String title = "Test Book";
        Book book = mock(Book.class);
        List<Book> books = Collections.singletonList(book);
        when(bookService.getBooks(eq(pageNumber), eq(title))).thenReturn(books);

        mockMvc.perform(get("/librarian/book")
                        .param("page", String.valueOf(pageNumber))
                        .param("title", title)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(bookService, times(1)).getBooks(eq(pageNumber), eq(title));
    }

    @Test
    void testGetBook() throws Exception {
        long bookId = 1L;
        Book book = mock(Book.class);
        when(bookService.getSingleBook(eq(bookId))).thenReturn(book);

        mockMvc.perform(get("/librarian/book/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bookService, times(1)).getSingleBook(eq(bookId));
    }

    @Test
    void testDeleteBook() throws Exception {
        long bookId = 1L;

        mockMvc.perform(delete("/librarian/book/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteBook(eq(bookId));
    }

    @Test
    void testAddBook() throws Exception {
        Book book = mock(Book.class);
        when(bookService.addBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/librarian/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).addBook(any(Book.class));
    }

    @Test
    void testUpdateBook() throws Exception {
        Book book = mock(Book.class);
        when(bookService.addBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/librarian/update-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).addBook(any(Book.class));
    }
}