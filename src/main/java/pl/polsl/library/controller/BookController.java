package pl.polsl.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
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
    public List<Book> getBooks(@RequestParam(required = false) Integer page){
        int pageNumber = page != null && page >= 0 ? page : 0;
        return bookService.getBooks(pageNumber);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable long id){
        return bookService.getSingleBook(id);
    }

}
