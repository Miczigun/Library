package pl.polsl.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.library.model.Book;
import pl.polsl.library.model.Loan;
import pl.polsl.library.model.MemberBooks;
import pl.polsl.library.model.MemberProjection;
import pl.polsl.library.repository.LoanRepository;
import pl.polsl.library.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibrarianService {

    private MemberRepository memberRepository;
    private LoanRepository loanRepository;

    public List<MemberProjection> getMembersForLibrarian() {
        return memberRepository.findAllMembers();
    }

    public MemberProjection getMemberForLibrarian(long id){
        return memberRepository.findMemberById(id);
    }
    //it has to be changed
    public MemberBooks userBooksForLibrarian(long id){
        List<Loan> userLoans = loanRepository.findByMemberId_Id(id);
        List<Book> userBooks = userLoans.stream().map(Loan::getBookId).collect(Collectors.toList());
        MemberProjection memberProjection = memberRepository.findMemberById(id);

        return new MemberBooks(memberProjection, userBooks);
    }
}
