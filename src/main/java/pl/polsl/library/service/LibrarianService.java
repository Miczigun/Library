package pl.polsl.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.library.model.dto.MemberProjection;
import pl.polsl.library.repository.LoanRepository;
import pl.polsl.library.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibrarianService {

    private MemberRepository memberRepository;
    private LoanRepository loanRepository;

    public List<MemberProjection> getMembersForLibrarian() {
        return memberRepository.findAllMembers();
    }

}
