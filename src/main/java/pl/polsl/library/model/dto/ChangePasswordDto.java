package pl.polsl.library.model.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * The {@code ChangePasswordDto} class represents a data transfer object for changing a user's password.
 */
@Getter
@Setter
public class ChangePasswordDto {

    /**
     * The current password of the user.
     */
    private String password;

    /**
     * The new password for the user, constrained by size (8 to 32 characters).
     */
    @Size(min = 8, max = 32)
    private String newPassword;

    /**
     * Constructor for creating a ChangePasswordDto object with specified current and new passwords.
     *
     * @param password    The current password of the user.
     * @param newPassword The new password for the user.
     */
    public ChangePasswordDto(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }
}
