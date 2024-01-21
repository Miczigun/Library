package pl.polsl.library.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.polsl.library.model.Member;
import pl.polsl.library.model.Role;
import pl.polsl.library.model.dto.RegisterMember;
import pl.polsl.library.repository.MemberRepository;
import pl.polsl.library.repository.RoleRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdminServiceTests {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLibrarian() {
        // Arrange
        RegisterMember registerMember = new RegisterMember("librarian@test.com", "password", "Librarian", "LastName", "Address");

        Role librarianRole = new Role("LIBRARIAN");
        when(roleRepository.findByAuthority("LIBRARIAN")).thenReturn(Optional.of(librarianRole));


        Member expectedMember = new Member("librarian@test.com", "encodedPassword", "Librarian", "LastName", "Address", Set.of(librarianRole));
        when(memberRepository.save(any(Member.class))).thenReturn(expectedMember);

        // Act
        Member createdMember = adminService.createLibrarian(registerMember);

        // Assert
        assertEquals(expectedMember, createdMember);

        // Verify interactions
        verify(roleRepository, times(1)).findByAuthority("LIBRARIAN");
        verify(encoder, times(1)).encode(registerMember.getPassword());
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void testSetRole() {
        // Arrange
        long memberId = 1L;
        String newRoleAuthority = "ADMIN";

        Member existingMember = new Member("user@test.com", "encodedPassword", "User", "LastName", "Address", Set.of());
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));

        Role newRole = new Role(newRoleAuthority);
        when(roleRepository.findByAuthority(newRoleAuthority)).thenReturn(Optional.of(newRole));

        // Act
        adminService.setRole(memberId, newRoleAuthority);

        // Assert
        Set<Role> expectedAuthorities = new HashSet<>();
        expectedAuthorities.add(newRole);

        assertEquals(expectedAuthorities, existingMember.getAuthorities());

        // Verify interactions
        verify(memberRepository, times(1)).findById(memberId);
        verify(roleRepository, times(1)).findByAuthority(newRoleAuthority);
        verify(memberRepository, times(1)).save(existingMember);
    }
}
