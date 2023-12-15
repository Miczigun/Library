package pl.polsl.library.model;

import lombok.Getter;
import lombok.Setter;

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
