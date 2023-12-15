package pl.polsl.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.library.model.*;
import pl.polsl.library.repository.LoanRepository;
import pl.polsl.library.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user is not valid"));
    }

    public List<Member> getMembers(){
        return memberRepository.findAll();
    }

    public Member getMember(long id){
        return memberRepository.findById(id).orElseThrow();
    }

    public List<MemberProjection> getMembersForLibrarian() {
        List<MemberProjection> members = memberRepository.findAllMembers();
        return members;
    }

    public MemberProjection getMemberForLibrarian(long id){
        return memberRepository.findMemberById(id);
    }

    public Member getMemberByEmail(String email){
        return memberRepository.findByEmail(email).orElseThrow();
    }

    public Member addMember(Member member){
        return memberRepository.save(member);
    }

    public void deleteMember(long id){
        memberRepository.deleteById(id);
    }

    public List<Book> userBooks(long id){
        List<Loan> userLoans = loanRepository.findByMemberId_Id(id);
        List<Book> userBooks = userLoans.stream().map(Loan::getBookId).collect(Collectors.toList());

        return userBooks;
    }

    public MemberBooks userBooksForLibrarian(long id){
        List<Loan> userLoans = loanRepository.findByMemberId_Id(id);
        List<Book> userBooks = userLoans.stream().map(Loan::getBookId).collect(Collectors.toList());
        MemberProjection memberProjection = memberRepository.findMemberById(id);

        MemberBooks memberBooks = new MemberBooks(memberProjection, userBooks);
        return memberBooks;
    }
}
