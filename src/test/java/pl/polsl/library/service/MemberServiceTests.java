package pl.polsl.library.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import pl.polsl.library.model.Book;
import pl.polsl.library.model.Loan;
import pl.polsl.library.model.Member;
import pl.polsl.library.model.dto.ChangePasswordDto;
import pl.polsl.library.model.dto.LoanProjection;
import pl.polsl.library.repository.BookRepository;
import pl.polsl.library.repository.LoanRepository;
import pl.polsl.library.repository.MemberRepository;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MemberServiceTests {


    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private BookService bookService;


    @Test
    void loadUserByUsername_ShouldReturnUserDetails() {
        String userEmail = "test@example.com";
        MemberRepository memberRepository = mock(MemberRepository.class);
        MemberService memberService = new MemberService(memberRepository, null, null, null,null);
        Member testMember = new Member();
        testMember.setEmail(userEmail);
        when(memberRepository.findByEmail(userEmail)).thenReturn(Optional.of(testMember));

        UserDetails userDetails = memberService.loadUserByUsername(userEmail);

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(userEmail);
    }

    @Test
    void loadUserByUsername_ShouldThrowUsernameNotFoundException() {
        String userEmail = "nonexistent@example.com";
        MemberRepository memberRepository = mock(MemberRepository.class);
        MemberService memberService = new MemberService(memberRepository, null, null, null,null);
        when(memberRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.loadUserByUsername(userEmail))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User is not valid");
    }

    @Test
    void getMembers_ShouldReturnListOfMembers() {
        MemberRepository memberRepository = mock(MemberRepository.class);
        MemberService memberService = new MemberService(memberRepository, null, null, null,null);

        Member testMember1 = new Member();
        testMember1.setId(1L);
        testMember1.setEmail("test1@example.com");

        Member testMember2 = new Member();
        testMember2.setId(2L);
        testMember2.setEmail("test2@example.com");

        List<Member> expectedMembers = Arrays.asList(testMember1, testMember2);

        when(memberRepository.findAll()).thenReturn(expectedMembers);

        List<Member> actualMembers = memberService.getMembers();

        assertThat(actualMembers).isNotNull();
        assertThat(actualMembers).hasSize(2);
        assertThat(actualMembers).containsExactlyElementsOf(expectedMembers);
    }

    @Test
    void getMember_ShouldReturnMemberById() {
        MemberRepository memberRepository = mock(MemberRepository.class);
        MemberService memberService = new MemberService(memberRepository, null, null, null,null);

        long memberId = 1L;
        Member testMember = new Member();
        testMember.setId(memberId);
        testMember.setEmail("test@example.com");

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(testMember));

        Member actualMember = memberService.getMember(memberId);

        assertThat(actualMember).isNotNull();
        assertThat(actualMember).isEqualTo(testMember);
    }

    @Test
    void getMember_ShouldThrowExceptionWhenNotFound() {
        MemberRepository memberRepository = mock(MemberRepository.class);
        MemberService memberService = new MemberService(memberRepository, null, null, null,null);

        long nonExistentMemberId = 999L;

        when(memberRepository.findById(nonExistentMemberId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.getMember(nonExistentMemberId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No value present");
    }

    @Test
    void getMemberByEmail_ShouldReturnMemberByEmail() {
        MemberRepository memberRepository = mock(MemberRepository.class);
        MemberService memberService = new MemberService(memberRepository, null, null, null,null);

        String memberEmail = "test@example.com";
        Member testMember = new Member();
        testMember.setId(1L);
        testMember.setEmail(memberEmail);

        when(memberRepository.findByEmail(memberEmail)).thenReturn(Optional.of(testMember));

        Member actualMember = memberService.getMemberByEmail(memberEmail);

        assertThat(actualMember).isNotNull();
        assertThat(actualMember).isEqualTo(testMember);
    }

    @Test
    void getMemberByEmail_ShouldThrowExceptionWhenNotFound() {
        MemberRepository memberRepository = mock(MemberRepository.class);
        MemberService memberService = new MemberService(memberRepository, null, null, null,null);

        String nonExistentMemberEmail = "nonexistent@example.com";

        when(memberRepository.findByEmail(nonExistentMemberEmail)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.getMemberByEmail(nonExistentMemberEmail))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No value present");
    }

    @Test
    void addMember_ShouldReturnAddedMember() {
        MemberRepository memberRepository = mock(MemberRepository.class);
        MemberService memberService = new MemberService(memberRepository, null, null, null,null);

        Member testMemberToAdd = new Member();
        testMemberToAdd.setId(1L);
        testMemberToAdd.setEmail("test@example.com");

        when(memberRepository.save(testMemberToAdd)).thenReturn(testMemberToAdd);

        Member addedMember = memberService.addMember(testMemberToAdd);

        assertThat(addedMember).isNotNull();
        assertThat(addedMember).isEqualTo(testMemberToAdd);
    }

    @Test
    void deleteMember_ShouldCallRepositoryDeleteById() {
        MemberRepository memberRepository = mock(MemberRepository.class);
        MemberService memberService = new MemberService(memberRepository, null, null, null,null);

        long memberIdToDelete = 1L;

        memberService.deleteMember(memberIdToDelete);

        verify(memberRepository, times(1)).deleteById(memberIdToDelete);
    }

    @Test
    void changePassword_ShouldChangePasswordAndReturnTrue() {
        MemberRepository memberRepository = mock(MemberRepository.class);

        PasswordEncoder encoder = mock(PasswordEncoder.class);
        MemberService memberService = new MemberService(memberRepository, null, null, null, encoder);

        long memberId = 1L;
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        ChangePasswordDto changePasswordDto = new ChangePasswordDto(oldPassword, newPassword);

        Member existingMember = new Member();
        existingMember.setId(memberId);
        existingMember.setPassword(encoder.encode(oldPassword));

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));

        when(encoder.matches(oldPassword, existingMember.getPassword())).thenReturn(true);

        when(encoder.encode(newPassword)).thenReturn("encodedNewPassword");

        boolean result = memberService.changePassword(memberId, changePasswordDto);

        assertThat(result).isTrue();
        assertThat(existingMember.getPassword()).isEqualTo("encodedNewPassword");
        verify(memberRepository, times(1)).save(existingMember);
    }

    @Test
    void changePassword_ShouldNotChangePasswordAndReturnFalse() {
        MemberRepository memberRepository = mock(MemberRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);

        MemberService memberService = new MemberService(memberRepository, null, null, null, encoder);

        long memberId = 1L;
        String incorrectOldPassword = "incorrectOldPassword";
        String newPassword = "newPassword";

        ChangePasswordDto changePasswordDto = new ChangePasswordDto(incorrectOldPassword,newPassword);
        changePasswordDto.setPassword(incorrectOldPassword);
        changePasswordDto.setNewPassword(newPassword);

        Member existingMember = new Member();
        existingMember.setId(memberId);
        existingMember.setPassword(encoder.encode("oldPassword"));

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));

        when(encoder.matches(incorrectOldPassword, existingMember.getPassword())).thenReturn(false);

        boolean result = memberService.changePassword(memberId, changePasswordDto);

        assertThat(result).isFalse();
        assertThat(existingMember.getPassword()).isEqualTo(encoder.encode("oldPassword")); // Hasło nie powinno być zmienione
        verify(memberRepository, never()).save(existingMember); // save nie powinno być wywołane
    }


    @Test
    void loanBook_ShouldLoanBookSuccessfully() {
        MemberRepository memberRepository = mock(MemberRepository.class);
        BookRepository bookRepository = mock(BookRepository.class);
        LoanRepository loanRepository = mock(LoanRepository.class);

        MemberService memberService = new MemberService(memberRepository, loanRepository, bookRepository, null,null);

        long memberId = 1L;
        long bookId = 2L;

        Member member = new Member();
        member.setId(memberId);
        member.setPenaltyPayment(0); // zalozenie braku kary

        Book book = new Book();
        book.setId(bookId);
        book.setQuantity(1);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(loanRepository.findActiveLoansByMemberId(memberId)).thenReturn(Collections.emptyList());

        boolean result = memberService.loanBook(memberId, bookId);

        assertThat(result).isTrue();
        assertThat(book.getQuantity()).isEqualTo(0);
        verify(bookRepository, times(1)).save(book);
        verify(loanRepository, times(1)).save(any(Loan.class));
    }


    @Test
    void returnBook_ShouldReturnBookSuccessfullyWithoutPenalty() {
        MemberRepository memberRepository = mock(MemberRepository.class);
        BookRepository bookRepository = mock(BookRepository.class);
        LoanRepository loanRepository = mock(LoanRepository.class);

        MemberService memberService = new MemberService(memberRepository, loanRepository, bookRepository, null,null);

        Member member = mock(Member.class);
        when(member.getId()).thenReturn(3L);

        Book book = mock(Book.class);
        when(book.getId()).thenReturn(2L);
        when(book.getQuantity()).thenReturn(1L);

        long loanId = 1L;

        Loan loan = new Loan(book,member);
        loan.setId(loanId);
        loan.setDueDate(LocalDate.now().plusDays(1)); // data oddania ustawiona na jutro

        loan.setBookId(book);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        int overdueDays = memberService.returnBook(loanId);

        assertThat(overdueDays).isEqualTo(0);
        assertThat(loan.getBookId().getQuantity()).isEqualTo(1);
        assertThat(loan.isReturnStatus()).isTrue();
        verify(loanRepository, times(1)).save(loan);

        verify(loan.getMemberId(), never()).setPenaltyPayment(anyInt());
    }

    @Test
    void returnBook_ShouldReturnBookWithPenalty() {
        // Arrange
        MemberRepository memberRepository = mock(MemberRepository.class);
        BookRepository bookRepository = mock(BookRepository.class);
        LoanRepository loanRepository = mock(LoanRepository.class);

        MemberService memberService = new MemberService(memberRepository, loanRepository, bookRepository, null,null);

        Member member = mock(Member.class);
        when(member.getId()).thenReturn(3L);

        Book book = mock(Book.class);
        when(book.getId()).thenReturn(2L);
        when(book.getQuantity()).thenReturn(1L);

        long loanId = 1L;

        Loan loan = new Loan(book,member);
        loan.setId(loanId);
        loan.setDueDate(LocalDate.now().minusDays(1)); // data oddania do wczoraj

        loan.setBookId(book);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        int overdueDays = memberService.returnBook(loanId);

        assertThat(overdueDays).isEqualTo(1);
        assertThat(loan.getBookId().getQuantity()).isEqualTo(1);
        assertThat(loan.isReturnStatus()).isTrue();
        verify(loanRepository, times(1)).save(loan);
        verify(loan.getMemberId(), times(1)).setPenaltyPayment(1);
    }

    @Test
    void getUserLoans_ShouldReturnListOfLoansForSpecificMember() {
        long userId = 1L;

        LoanProjection loan1 = mock(LoanProjection.class);
        when(loan1.getId()).thenReturn(1L);
        when(loan1.getCheckOutDate()).thenReturn(LocalDate.now().minusDays(7));
        when(loan1.getDueDate()).thenReturn(LocalDate.now().plusDays(7));
        when(loan1.getReturnDate()).thenReturn(null);
        when(loan1.isReturnStatus()).thenReturn(false);
        when(loan1.getTitle()).thenReturn("Book 1");
        when(loan1.getBookId()).thenReturn("B001");

        LoanProjection loan2 = mock(LoanProjection.class);
        when(loan2.getId()).thenReturn(2L);
        when(loan2.getCheckOutDate()).thenReturn(LocalDate.now().minusDays(14));
        when(loan2.getDueDate()).thenReturn(LocalDate.now().minusDays(7));
        when(loan2.getReturnDate()).thenReturn(LocalDate.now().minusDays(2));
        when(loan2.isReturnStatus()).thenReturn(true);
        when(loan2.getTitle()).thenReturn("Book 2");
        when(loan2.getBookId()).thenReturn("B002");

        List<LoanProjection> expectedLoans = Arrays.asList(loan1, loan2);

        LoanRepository loanRepository = mock(LoanRepository.class);
        when(loanRepository.findMemberLoans(userId)).thenReturn(expectedLoans);

        MemberService memberService = new MemberService(null, loanRepository, null, null,null);

        List<LoanProjection> actualLoans = memberService.getUserLoans(userId);

        assertThat(actualLoans).isNotNull();
        assertThat(actualLoans).hasSize(2);
        assertThat(actualLoans).containsExactlyElementsOf(expectedLoans);
    }

    @Test
    void changeAddress_ShouldChangeAddressSuccessfully() {
        long memberId = 1L;
        String newAddress = "New Address";

        MemberRepository memberRepository = mock(MemberRepository.class);
        MemberService memberService = new MemberService(memberRepository, null, null, null,null);

        Member existingMember = new Member();
        existingMember.setId(memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));

        boolean result = memberService.changeAddress(memberId, newAddress);

        assertThat(result).isTrue();
        assertThat(existingMember.getAddress()).isEqualTo(newAddress);
        verify(memberRepository, times(1)).save(existingMember);
    }

    @Test
    void changeAddress_ShouldNotChangeAddressIfInvalid() {
        long memberId = 1L;

        //251 znakow
        String newAddress = "Jest to bardzo dlugi adres email, który przekracza maksymalna dozwolona dlugosc 250 znakow. Ten adres jest celowo długi, aby przetestowac weryfikacje." +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVW";

        //System.out.println(newAddress.length());

        MemberRepository memberRepository = mock(MemberRepository.class);
        MemberService memberService = new MemberService(memberRepository, null, null, null,null);

        Member existingMember = new Member();
        existingMember.setId(memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));

        boolean result = memberService.changeAddress(memberId, newAddress);

        assertThat(result).isFalse();
        assertThat(existingMember.getAddress()).isNull();
        verify(memberRepository, never()).save(existingMember);
    }

    @Test
    void getPenaltyPayment_ShouldReturnPenaltyPaymentForExistingMember() {
        long memberId = 1L;
        int expectedPenaltyPayment = 10;

        MemberRepository memberRepository = mock(MemberRepository.class);
        MemberService memberService = new MemberService(memberRepository, null, null, null,null);

        Member existingMember = new Member();
        existingMember.setId(memberId);
        existingMember.setPenaltyPayment(expectedPenaltyPayment);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));

        int actualPenaltyPayment = memberService.getPenaltyPayment(memberId);

        assertEquals(expectedPenaltyPayment, actualPenaltyPayment);
        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    void getPenaltyPayment_ShouldThrowExceptionForNonExistingMember() {
        long memberId = 2L;

        MemberRepository memberRepository = mock(MemberRepository.class);
        MemberService memberService = new MemberService(memberRepository, null, null, null, null);

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> memberService.getPenaltyPayment(memberId));
        verify(memberRepository, times(1)).findById(memberId);
    }
}
