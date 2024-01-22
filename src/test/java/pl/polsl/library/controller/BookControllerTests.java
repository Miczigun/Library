package pl.polsl.library.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.polsl.library.model.Book;
import pl.polsl.library.service.BookService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTests {
    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void testGetBooks() throws Exception {
        // Arrange
        when(bookService.getBooks(anyInt(), nullable(String.class)))
                .thenReturn(Collections.singletonList(new Book()));

        // Act and Assert
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // Verify
        verify(bookService, times(1)).getBooks(anyInt(), nullable(String.class));
    }

    @Test
    void testGetBook() throws Exception {
        long bookId = 1L;
        when(bookService.getSingleBook(bookId)).thenReturn(new Book());

        mockMvc.perform(get("/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap());

        verify(bookService, times(1)).getSingleBook(bookId);
    }
}
