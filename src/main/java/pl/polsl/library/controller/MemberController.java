package pl.polsl.library.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.polsl.library.model.Member;
import pl.polsl.library.model.dto.ChangePasswordDto;
import pl.polsl.library.model.dto.LoanProjection;
import pl.polsl.library.service.MemberService;

import java.util.List;
import java.util.Map;

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
    public List<LoanProjection> userBooks(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Member member = memberService.getMemberByEmail(userEmail);

        return memberService.getUserLoans(member.getId());
    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(@Valid @RequestBody ChangePasswordDto changePassword, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(Map.of("message","New password must have minimum 8 characters"), HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Member member = memberService.getMemberByEmail(userEmail);

        if (memberService.changePassword(member.getId(), changePassword)){
            return new ResponseEntity<>(Map.of("message","Success"),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("message","Invalid password"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/change-address")
    public ResponseEntity<Map<String, String>> changeAddress(String address){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Member member = memberService.getMemberByEmail(userEmail);

        if (memberService.changeAddress(member.getId(), address)){
            return new ResponseEntity<>(Map.of("message", "Success"),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("message", "Address can not be empty and has maximum 250 characters"), HttpStatus.BAD_REQUEST);
        }
    }
}
