package pl.polsl.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.polsl.library.model.Book;
import pl.polsl.library.model.Loan;
import pl.polsl.library.model.Member;
import pl.polsl.library.model.dto.LoanDto;
import pl.polsl.library.repository.LoanRepository;
import pl.polsl.library.service.MemberService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin("*")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/loan/{id}")
    public ResponseEntity<String> loanBook(@PathVariable long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Member member = memberService.getMemberByEmail(userEmail);
        boolean loanStatus = memberService.loanBook(member.getId(), id);

        if (loanStatus) {
            return ResponseEntity.ok("Book loan successful.");
        } else {
            return ResponseEntity.badRequest().body("Unable to loan the book.");
        }
    }

    @PostMapping("/return/{id}")
    public int returnBook(@PathVariable long id){
        return memberService.returnBook(id);
    }

    @GetMapping("/books")
    public List<LoanDto> userBooks(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Member member = memberService.getMemberByEmail(userEmail);

        return memberService.getUserLoans(member.getId());
    }

}
