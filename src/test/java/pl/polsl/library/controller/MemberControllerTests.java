package pl.polsl.library.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.polsl.library.model.Member;
import pl.polsl.library.repository.MemberRepository;
import pl.polsl.library.service.MemberService;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



public class MemberControllerTests {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }
    @Mock
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberController memberController;

    private MockMvc mockMvc;

    @Test
    void testLoanBook_SuccessfulLoan() throws Exception {
        long bookId = 1L;
        Member member = new Member();
        member.setId(1L);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(memberService.getMemberByEmail(anyString())).thenReturn(member);
        when(memberService.loanBook(anyLong(), anyLong())).thenReturn(true);

        mockMvc.perform(post("/users/loan/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Book loan successful."));

        verify(memberService, times(1)).getMemberByEmail(anyString());
        verify(memberService, times(1)).loanBook(eq(member.getId()), eq(bookId));
    }
    @Test
    void testLoanBook_UnsuccessfulLoan() throws Exception {
        long bookId = 1L;
        Member member = new Member();
        member.setId(1L);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(memberService.getMemberByEmail(anyString())).thenReturn(member);
        when(memberService.loanBook(anyLong(), anyLong())).thenReturn(false);

        mockMvc.perform(post("/users/loan/{id}", bookId))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Unable to loan the book."));

        verify(memberService, times(1)).getMemberByEmail(anyString());
        verify(memberService, times(1)).loanBook(eq(member.getId()), eq(bookId));
    }

    @Test
    void testGetPenaltyPayment() throws Exception {
        Member member = new Member();
        member.setId(1L);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(memberService.getMemberByEmail(anyString())).thenReturn(member);
        when(memberService.getPenaltyPayment(anyLong())).thenReturn(10); // Dla przykładu ustawiamy wartość 10.

        mockMvc.perform(get("/users/penalty-payment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(10)); // Sprawdzamy, czy wartość zwrócona to 10.

        verify(memberService, times(1)).getMemberByEmail(anyString());
        verify(memberService, times(1)).getPenaltyPayment(eq(member.getId()));
    }

}
