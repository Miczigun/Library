package pl.polsl.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polsl.library.model.LoginMember;
import pl.polsl.library.model.Member;
import pl.polsl.library.model.RegisterMember;
import pl.polsl.library.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public Member registerUser(@RequestBody RegisterMember body){
        return authenticationService.registerUser(body.getEmail(), body.getPassword());
    }

    @PostMapping("/login")
    public LoginMember loginUser(@RequestBody RegisterMember body){
        return authenticationService.loginUser(body.getEmail(), body.getPassword());
    }
}
