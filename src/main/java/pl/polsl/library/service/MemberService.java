package pl.polsl.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.library.model.Book;
import pl.polsl.library.model.Loan;
import pl.polsl.library.model.Member;
import pl.polsl.library.repository.LoanRepository;
import pl.polsl.library.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    public List<Member> getMembers(){
        return memberRepository.findAll();
    }

    public Member getMember(long id){
        return memberRepository.findById(id).orElseThrow();
    }

    public Member getMemberByEmail(String email){
        return memberRepository.findByEmail(email);
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
}
