package pl.polsl.library.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.polsl.library.model.Member;
import pl.polsl.library.model.dto.RegisterMember;
import pl.polsl.library.service.MemberService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    @GetMapping("/user")
    public List<Member> getUsers(){
        return memberService.getMembers();
    }

    @GetMapping("/user/{id}")
    public Member getUser(@PathVariable long id){
        return memberService.getMember(id);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable long id){
        memberService.deleteMember(id);
    }

    @PostMapping("/users")
    public Member addUser(@RequestBody Member member){
        return memberService.addMember(member);
    }

    @PostMapping("/create-librarian")
    public ResponseEntity<Map<String, String>> createLibrarian(@Valid @RequestBody RegisterMember registerMember, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(Map.of("message","Invalid Data"), HttpStatus.BAD_REQUEST);
        }
        memberService.createLibrarian(registerMember);
        return new ResponseEntity<>(Map.of("message", "Success"), HttpStatus.OK);
    }
}
