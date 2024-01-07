package pl.polsl.library.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.polsl.library.model.dto.LoginMember;
import pl.polsl.library.model.Member;
import pl.polsl.library.model.dto.RegisterMember;
import pl.polsl.library.service.AuthenticationService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@Valid @RequestBody RegisterMember body, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(Map.of("message","Username can not be empty and password must have minimum 8 characters"), HttpStatus.BAD_REQUEST);
        } else if (authenticationService.existsUser(body.getEmail())){
            return new ResponseEntity<>(Map.of("message","That email is taken"), HttpStatus.BAD_REQUEST);
        } else {
            authenticationService.registerUser(body);
            return new ResponseEntity<>(Map.of("message","User registered successfully"), HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        LoginMember loginMember = authenticationService.loginUser(email, password);

        if (loginMember.getMember() != null) {
            return new ResponseEntity<>(loginMember, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("message", "Invalid credentials"), HttpStatus.UNAUTHORIZED);
        }
    }
}
