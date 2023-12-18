package pl.polsl.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.library.model.*;
import pl.polsl.library.model.dto.LoanDto;
import pl.polsl.library.repository.BookRepository;
import pl.polsl.library.repository.LoanRepository;
import pl.polsl.library.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User is not valid"));
    }

    public List<Member> getMembers(){

        return memberRepository.findAll();
    }

    public Member getMember(long id){
        return memberRepository.findById(id).orElseThrow();
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

        return userLoans.stream().map(Loan::getBookId).collect(Collectors.toList());
    }

    public boolean loanBook(long memberId, long bookId){
        Member member = memberRepository.findById(memberId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        if (book.getQuantity() < 1) { return false;}
        if (member.getPenaltyPayment() > 0) { return false;}

        List<Long> activeLoans = loanRepository.findActiveLoansByMemberId(memberId);
        if (!activeLoans.isEmpty()) { return false;}

        Loan loan = new Loan(book, member);
        if (book.getQuantity() > 20) {
            loan.setDueDate(loan.getCheckOutDate().plusWeeks(2));
        } else{
            loan.setDueDate(loan.getCheckOutDate().plusWeeks(1));
        }

        long bookQuantity = book.getQuantity() - 1;
        book.setQuantity(bookQuantity);

        bookRepository.save(book);
        loanRepository.save(loan);
        return true;
    }

    public List<LoanDto> getUserLoans(long userId) {
        List<Loan> loans = loanRepository.findByMemberId_Id(userId);
        List<LoanDto> loanDTOs = new ArrayList<>();

        for (Loan loan : loans) {
            LoanDto loanDto = new LoanDto(
                    loan.getId(),
                    loan.getBookId().getId(),
                    loan.getBookId().getTitle(),
                    loan.getCheckOutDate(),
                    loan.getDueDate(),
                    loan.getReturnDate(),
                    loan.isReturnStatus()
            );
            loanDTOs.add(loanDto);
        }

        return loanDTOs;
    }

}
