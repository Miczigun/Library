package pl.polsl.library.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import pl.polsl.library.model.dto.MemberProjection;
import pl.polsl.library.repository.LoanRepository;
import pl.polsl.library.repository.MemberRepository;

import java.util.Arrays;
import java.util.List;

public class LibrarianServiceTests {

    @Test
    void getMembersForLibrarian_ShouldReturnMemberProjections() {
        MemberRepository memberRepository = mock(MemberRepository.class);
        LoanRepository loanRepository = mock(LoanRepository.class);

        LibrarianService librarianService = new LibrarianService(memberRepository);

        MemberProjection memberProjection1 = mock(MemberProjection.class);
        when(memberProjection1.getId()).thenReturn(1L);
        when(memberProjection1.getEmail()).thenReturn("john.doe@example.com");
        when(memberProjection1.getName()).thenReturn("John");
        when(memberProjection1.getSurname()).thenReturn("Doe");
        when(memberProjection1.getAddress()).thenReturn("123 Main St");

        MemberProjection memberProjection2 = mock(MemberProjection.class);
        when(memberProjection2.getId()).thenReturn(2L);
        when(memberProjection2.getEmail()).thenReturn("jane.smith@example.com");
        when(memberProjection2.getName()).thenReturn("Jane");
        when(memberProjection2.getSurname()).thenReturn("Smith");
        when(memberProjection2.getAddress()).thenReturn("456 Oak St");

        List<MemberProjection> expectedMemberProjections = Arrays.asList(memberProjection1, memberProjection2);

        when(memberRepository.findAllMembers()).thenReturn(expectedMemberProjections);

        List<MemberProjection> actualMemberProjections = librarianService.getMembersForLibrarian();

        assertEquals(expectedMemberProjections, actualMemberProjections);
        verify(memberRepository, times(1)).findAllMembers();
    }
}
