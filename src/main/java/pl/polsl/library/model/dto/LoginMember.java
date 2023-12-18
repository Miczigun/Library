package pl.polsl.library.model.dto;

import lombok.Getter;
import lombok.Setter;
import pl.polsl.library.model.Member;

@Getter
@Setter
public class LoginMember {
    private Member member;
    private String jwt;

    public LoginMember(){
        super();
    }

    public LoginMember(Member member, String jwt){
        this.member = member;
        this.jwt = jwt;
    }

}
