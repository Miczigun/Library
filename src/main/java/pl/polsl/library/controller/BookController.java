package pl.polsl.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import pl.polsl.library.model.Book;
import pl.polsl.library.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
@CrossOrigin("*")
public class BookController {

    private final BookService bookService;

    @GetMapping("")
    public ResponseEntity<List<Book>> getBooks(@RequestParam(required = false) Integer page,
                               @RequestParam(required = false) String title){

        int pageNumber = page != null && page >= 0 ? page : 0;
        List<Book> books = bookService.getBooks(pageNumber, title);
        int totalPages = bookService.totalPages();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(totalPages));

        return new ResponseEntity<>(books, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable long id){
        return bookService.getSingleBook(id);
    }

}
