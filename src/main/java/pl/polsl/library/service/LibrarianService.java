package pl.polsl.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.library.model.dto.MemberProjection;
import pl.polsl.library.repository.LoanRepository;
import pl.polsl.library.repository.MemberRepository;

import java.util.List;

/**
 * The {@code LibrarianService} class provides services specific to librarian-related operations,
 * such as retrieving information about library members.
 */
@Service
@RequiredArgsConstructor
public class LibrarianService {

    private final MemberRepository memberRepository;

    /**
     * Get a list of members with basic information for librarian use.
     *
     * @return List of MemberProjection containing essential details about library members.
     */
    public List<MemberProjection> getMembersForLibrarian() {
        return memberRepository.findAllMembers();
    }

}
