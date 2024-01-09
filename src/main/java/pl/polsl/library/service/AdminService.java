package pl.polsl.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.library.model.Member;
import pl.polsl.library.model.Role;
import pl.polsl.library.model.dto.RegisterMember;
import pl.polsl.library.repository.MemberRepository;
import pl.polsl.library.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * The {@code AdminService} class provides services specific to administrative tasks,
 * such as creating librarian accounts and updating user roles.
 */
@Service
@RequiredArgsConstructor
public class AdminService {

    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * Create a new librarian account with the provided registration information.
     *
     * @param librarian The registration information for the new librarian.
     * @return The created librarian member.
     */
    public Member createLibrarian(RegisterMember librarian){
        String encodedPassword = encoder.encode(librarian.getPassword());
        Role userRole = roleRepository.findByAuthority("LIBRARIAN").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        Member member = new Member(librarian.getEmail(),encodedPassword, librarian.getName(), librarian.getSurname(), librarian.getAddress(), authorities);
        return memberRepository.save(member);
    }

    /**
     * Set the role of a member identified by their ID.
     *
     * @param memberId The ID of the member.
     * @param role     The new role to be assigned to the member.
     */
    public void setRole(long memberId, String role){
        Member member = memberRepository.findById(memberId).orElseThrow();
        Role newRole = roleRepository.findByAuthority(role).get();

        Set<Role> authorities = new HashSet<>();
        authorities.add(newRole);
        member.setAuthorities(authorities);

        memberRepository.save(member);
    }
}
