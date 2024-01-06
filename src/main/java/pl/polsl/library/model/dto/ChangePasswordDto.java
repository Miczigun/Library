package pl.polsl.library.model.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDto {
    private String password;
    @Size(min = 8, max = 32)
    private String newPassword;

    public ChangePasswordDto(String password, String newPassword){
        this.password = password;
        this.newPassword = newPassword;
    }
}
