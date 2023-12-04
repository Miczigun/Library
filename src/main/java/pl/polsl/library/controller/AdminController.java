package pl.polsl.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.polsl.library.model.Member;
import pl.polsl.library.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/admin")
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
}
