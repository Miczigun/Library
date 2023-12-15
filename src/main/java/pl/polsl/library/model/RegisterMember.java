package pl.polsl.library.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterMember {
    private String email;
    private String password;

    public RegisterMember(){
        super();
    }

    public RegisterMember(String email, String password){
        super();
        this.email = email;
        this.password = password;
    }

}
