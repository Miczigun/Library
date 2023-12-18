package pl.polsl.library.model.dto;

import lombok.Getter;
import lombok.Setter;
import pl.polsl.library.model.Book;
import pl.polsl.library.model.dto.MemberProjection;

import java.util.List;

@Getter
@Setter
public class MemberBooks {

    private MemberProjection member;
    private List<Book> userBooks;

    public MemberBooks(MemberProjection member, List<Book> userBooks) {
        this.member = member;
        this.userBooks = userBooks;
    }
}
