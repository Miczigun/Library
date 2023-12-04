package pl.polsl.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.polsl.library.model.Book;
import pl.polsl.library.model.Member;
import pl.polsl.library.model.MemberBooks;
import pl.polsl.library.model.MemberProjection;
import pl.polsl.library.service.BookService;
import pl.polsl.library.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/librarian")
@RequiredArgsConstructor
public class LibrarianController {

    private final MemberService memberService;
    private final BookService bookService;

    @GetMapping("/user")
    public List<MemberProjection> getMembers(){
        return memberService.getMembersForLibrarian();
    }

    @GetMapping("/user/{id}")
    public MemberBooks getMemberBooks(@PathVariable long id){
        return memberService.userBooksForLibrarian(id);
    }

    @GetMapping("/book")
    public List<Book> getBooks(){
        return bookService.getBooks();
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

}
