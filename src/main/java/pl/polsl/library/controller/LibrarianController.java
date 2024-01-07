package pl.polsl.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.polsl.library.model.Book;
import pl.polsl.library.model.dto.LoanProjection;
import pl.polsl.library.model.dto.MemberProjection;
import pl.polsl.library.service.BookService;
import pl.polsl.library.service.LibrarianService;
import pl.polsl.library.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/librarian")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LibrarianController {

    private final LibrarianService librarianService;
    private final BookService bookService;
    private final MemberService memberService;

    @GetMapping("/user")
    public List<MemberProjection> getMembers(){
        return librarianService.getMembersForLibrarian();
    }

    @GetMapping("/user/{id}")
    public List<LoanProjection> getMemberBooks(@PathVariable long id){
        return memberService.getUserLoans(id);
    }

    @GetMapping("/book")
    public List<Book> getBooks(@RequestParam(required = false) Integer page){
        int pageNumber = page != null && page >= 0 ? page : 0;
        return bookService.getBooks(pageNumber);
    }

    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable long id){
        return bookService.getSingleBook(id);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable long id){
        bookService.deleteBook(id);
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    @PutMapping("/update-book")
    public Book updateBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

}
