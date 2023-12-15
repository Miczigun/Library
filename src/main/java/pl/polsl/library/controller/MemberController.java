package pl.polsl.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.polsl.library.model.Book;
import pl.polsl.library.model.Member;
import pl.polsl.library.service.MemberService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin("*")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{id}/books")
    public List<Book> userBooks(@PathVariable long id){
        return memberService.userBooks(id);
    }

    @GetMapping("/delete/{id}")
    public void deleteUser(@PathVariable long id){
        memberService.deleteMember(id);
    }

    @GetMapping("/{id}")
    public Member getMember(@PathVariable long id){
        return memberService.getMember(id);
    }
}
