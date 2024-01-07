package pl.polsl.library.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterMember {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 8, max = 32)
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    @Size(min = 1, max = 250)
    private String address;

    public RegisterMember(){
        super();
    }

    public RegisterMember(String email, String password){
        super();
        this.email = email;
        this.password = password;
    }

    public RegisterMember(String email, String password, String name, String surname, String address){
        super();
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

}
