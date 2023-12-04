package pl.polsl.library.model;

import lombok.Getter;
import lombok.Setter;

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
