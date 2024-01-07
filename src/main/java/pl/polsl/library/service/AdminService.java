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

@Service
@RequiredArgsConstructor
public class AdminService {

    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder encoder;

    public Member createLibrarian(RegisterMember librarian){
        String encodedPassword = encoder.encode(librarian.getPassword());
        Role userRole = roleRepository.findByAuthority("LIBRARIAN").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        Member member = new Member(librarian.getEmail(),encodedPassword, librarian.getName(), librarian.getSurname(), librarian.getAddress(), authorities);
        return memberRepository.save(member);
    }

    public void setRole(long memberId, String role){
        Member member = memberRepository.findById(memberId).orElseThrow();
        Role newRole = roleRepository.findByAuthority(role).get();

        Set<Role> authorities = new HashSet<>();
        authorities.add(newRole);
        member.setAuthorities(authorities);

        memberRepository.save(member);
    }
}
