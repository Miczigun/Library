package pl.polsl.library.service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.library.model.dto.LoginMember;
import pl.polsl.library.model.Member;
import pl.polsl.library.model.Role;
import pl.polsl.library.model.dto.RegisterMember;
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

    public Member registerUser(RegisterMember body){

        String encodedPassword = passwordEncoder.encode(body.getPassword());
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        Member member = new Member(body.getEmail(), encodedPassword, body.getName(), body.getSurname(), body.getAddress(), authorities);

        return memberRepository.save(member);
    }

    public LoginMember loginUser(String email, String password){

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            String token = tokenService.generateJwt(auth);
            // it can be bad solution!
            return new LoginMember(memberRepository.findByEmail(email).get(), token);

        } catch(AuthenticationException e){
            return new LoginMember(null, "");
        }
    }

    public boolean existsUser(String email){
        try{
            Member member = memberRepository.findByEmail(email).orElseThrow();
            return true;
        } catch (NoSuchElementException e){
            return false;
        }
    }

}
