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

/**
 * The {@code MemberService} class provides services related to Member entities, including user authentication,
 * CRUD operations, password management, book loans, and member details retrieval.
 */
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder encoder;

    /**
     * Load user details by username (email) for authentication purposes.
     *
     * @param email The email (username) of the user.
     * @return UserDetails containing information about the user.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User is not valid"));
    }

    /**
     * Get a list of all members in the library.
     *
     * @return List of all members.
     */
    public List<Member> getMembers(){

        return memberRepository.findAll();
    }

    /**
     * Get a specific member by ID.
     *
     * @param id The ID of the member.
     * @return The member with the specified ID.
     */
    public Member getMember(long id){
        return memberRepository.findById(id).orElseThrow();
    }

    /**
     * Get a specific member by email.
     *
     * @param email The email of the member.
     * @return The member with the specified email.
     */
    public Member getMemberByEmail(String email){
        return memberRepository.findByEmail(email).orElseThrow();
    }

    /**
     * Add a new member to the library.
     *
     * @param member The member to be added.
     * @return The added member.
     */
    public Member addMember(Member member){
        return memberRepository.save(member);
    }

    /**
     * Delete a member by ID.
     *
     * @param id The ID of the member to be deleted.
     */
    public void deleteMember(long id){
        memberRepository.deleteById(id);
    }

    /**
     * Change the password for a member.
     *
     * @param memberId      The ID of the member.
     * @param changePassword The DTO containing old and new password information.
     * @return True if the password is successfully changed, false otherwise.
     */
    public boolean changePassword(long memberId, ChangePasswordDto changePassword){
        Member member = memberRepository.findById(memberId).orElseThrow();
        boolean correctPassword = encoder.matches(changePassword.getPassword(), member.getPassword());

        if (!correctPassword) {
            return false;
        }
        if (changePassword.getPassword().equals(changePassword.getNewPassword())){
            return false;
        }

        String encodedPassword = encoder.encode(changePassword.getNewPassword());
        member.setPassword(encodedPassword);
        memberRepository.save(member);

        return true;
    }

    /**
     * Loan a book to a member.
     *
     * @param memberId The ID of the member.
     * @param bookId   The ID of the book to be loaned.
     * @return True if the book is successfully loaned, false otherwise.
     */
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

    /**
     * Return a book from loan and calculate any penalty.
     *
     * @param loanId The ID of the loan.
     * @return The number of days the book is overdue.
     */
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

    /**
     * Get a list of loans for a specific member.
     *
     * @param userId The ID of the member.
     * @return List of loans associated with the member.
     */
    public List<LoanProjection> getUserLoans(long userId) {
        return loanRepository.findMemberLoans(userId);
    }

    /**
     * Change the address of a member.
     *
     * @param memberId The ID of the member.
     * @param address   The new address to be set.
     * @return True if the address is successfully changed, false otherwise.
     */
    public boolean changeAddress(long memberId, String address){
        Member member = memberRepository.findById(memberId).orElseThrow();
        if (!address.isEmpty() && address.length() <= 250){
            member.setAddress(address);
            memberRepository.save(member);
            return true;
        }

        return false;
    }

    public int getPenaltyPayment(long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow();
        return member.getPenaltyPayment();
    }

    public void payFine(long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.setPenaltyPayment(0);
        memberRepository.save(member);
    }
}
