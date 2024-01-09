package pl.polsl.library.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * The {@code RegisterMember} class represents a data transfer object for registering a new library member.
 * It includes fields for email, password, name, surname, and address with validation annotations.
 */
@Getter
@Setter
public class RegisterMember {

    /**
     * The email address of the new member, validated to ensure it is not blank and follows the email format.
     */
    @Email
    @NotBlank
    private String email;

    /**
     * The password for the new member, validated to ensure it is not blank and has a size between 8 and 32 characters.
     */
    @NotBlank
    @Size(min = 8, max = 32)
    private String password;

    /**
     * The first name of the new member.
     */
    @NotBlank
    private String name;

    /**
     * The last name of the new member.
     */
    @NotBlank
    private String surname;

    /**
     * The address of the new member, validated to ensure it is not blank and has a size between 1 and 250 characters.
     */
    @NotBlank
    @Size(min = 1, max = 250)
    private String address;

    /**
     * Default constructor for the RegisterMember class.
     */
    public RegisterMember() {
        super();
    }

    /**
     * Constructor for creating a RegisterMember object with specified email and password.
     *
     * @param email    The email address of the new member.
     * @param password The password for the new member.
     */
    public RegisterMember(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor for creating a RegisterMember object with detailed information.
     *
     * @param email    The email address of the new member.
     * @param password The password for the new member.
     * @param name     The first name of the new member.
     * @param surname  The last name of the new member.
     * @param address  The address of the new member.
     */
    public RegisterMember(String email, String password, String name, String surname, String address) {
        super();
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
    }
}
