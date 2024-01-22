package pl.polsl.library.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.polsl.library.model.Member;
import pl.polsl.library.model.Role;
import pl.polsl.library.model.dto.RegisterMember;
import pl.polsl.library.repository.MemberRepository;
import pl.polsl.library.repository.RoleRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthenticationServiceTests {

    @Autowired
    private AuthenticationService authenticationService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Test
    void registerUser_ShouldRegisterNewUser() {
        RegisterMember registerMember = new RegisterMember();
        registerMember.setEmail("test@example.com");
        registerMember.setPassword("password");
        registerMember.setName("John");
        registerMember.setSurname("Doe");
        registerMember.setAddress("123 Main St");

        Role userRole = new Role("USER");
        Optional<Role> optionalUserRole = Optional.of(userRole);

        when(roleRepository.findByAuthority("USER")).thenReturn(optionalUserRole);

        when(passwordEncoder.encode(registerMember.getPassword())).thenReturn("encodedPassword");

        Member savedMember = new Member();
        savedMember.setId(1L);
        savedMember.setEmail(registerMember.getEmail());
        savedMember.setPassword("encodedPassword");
        savedMember.setName(registerMember.getName());
        savedMember.setSurname(registerMember.getSurname());
        savedMember.setAddress(registerMember.getAddress());
        savedMember.setAuthorities(Collections.singleton(userRole));

        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

        Member result = authenticationService.registerUser(registerMember);

        assertNotNull(result);
        assertEquals(savedMember.getId(), result.getId());
        assertEquals(savedMember.getEmail(), result.getEmail());
        assertEquals(savedMember.getName(), result.getName());
        assertEquals(savedMember.getSurname(), result.getSurname());
        assertEquals(savedMember.getAddress(), result.getAddress());
        assertEquals(savedMember.getAuthorities(), result.getAuthorities());

        verify(roleRepository, times(1)).findByAuthority("USER");
        verify(passwordEncoder, times(1)).encode(registerMember.getPassword());
        verify(memberRepository, times(1)).save(any(Member.class));

    }

    @Test
    void existsUser_ShouldReturnTrueIfUserExists() {
        String existingEmail = "existing@example.com";

        Member existingMember = new Member();
        existingMember.setEmail(existingEmail);

        when(memberRepository.findByEmail(existingEmail)).thenReturn(Optional.of(existingMember));

        boolean result = authenticationService.existsUser(existingEmail);
        System.out.println(result);
        assertTrue(result);
        verify(memberRepository, times(1)).findByEmail(existingEmail);
    }

    @Test
    void existsUser_ShouldReturnFalseIfUserDoesNotExist() {
        String nonExistingEmail = "nonexisting@example.com";

        when(memberRepository.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());

        boolean result = authenticationService.existsUser(nonExistingEmail);

        assertFalse(result);
        verify(memberRepository, times(1)).findByEmail(nonExistingEmail);
    }

}
