package pl.polsl.library.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.library.model.LoginMember;
import pl.polsl.library.model.Member;
import pl.polsl.library.model.Role;
import pl.polsl.library.repository.MemberRepository;
import pl.polsl.library.repository.RoleRepository;


@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public Member registerUser(String email, String password){

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        return memberRepository.save(new Member(email, encodedPassword, authorities));
    }

    public LoginMember loginUser(String email, String password){

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            System.out.println(auth);

            String token = tokenService.generateJwt(auth);
            // it can be bad solution!
            return new LoginMember(memberRepository.findByEmail(email).get(), token);

        } catch(AuthenticationException e){
            return new LoginMember(null, "");
        }
    }

}
