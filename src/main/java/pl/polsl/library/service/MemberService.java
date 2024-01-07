package pl.polsl.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.library.model.Book;
import pl.polsl.library.model.Loan;
import pl.polsl.library.model.Member;
import pl.polsl.library.model.dto.ChangePasswordDto;
import pl.polsl.library.model.dto.LoanProjection;
import pl.polsl.library.repository.BookRepository;
import pl.polsl.library.repository.LoanRepository;
import pl.polsl.library.repository.MemberRepository;
import pl.polsl.library.repository.RoleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final RoleRepository roleRepository;

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

    public boolean changePassword(long memberId, ChangePasswordDto changePassword){
        Member member = memberRepository.findById(memberId).orElseThrow();
        if (encoder.matches(changePassword.getPassword(), member.getPassword())) {
            return false;
        }

        String encodedPassword = encoder.encode(changePassword.getNewPassword());
        member.setPassword(encodedPassword);
        memberRepository.save(member);

        return true;
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

    public int returnBook(long loanId){
        Loan loan = loanRepository.findById(loanId).orElseThrow();

        loan.setReturnDate(LocalDate.now());
        int days = loan.getReturnDate().compareTo(loan.getDueDate());

        if (days <= 0){
            days = 0;
        } else {
            loan.getMemberId().setPenaltyPayment(days);
        }

        loan.getBookId().setQuantity(loan.getBookId().getQuantity() + 1);
        loan.setReturnStatus(true);
        loanRepository.save(loan);
        return days;
    }

    public List<LoanProjection> getUserLoans(long userId) {
        return loanRepository.findMemberLoans(userId);
    }

    public boolean changeAddress(long memberId, String address){
        Member member = memberRepository.findById(memberId).orElseThrow();
        if (!address.isEmpty() && address.length() < 250){
            member.setAddress(address);
            memberRepository.save(member);
            return true;
        }

        return false;
    }
}
