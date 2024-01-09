package pl.polsl.library.model.dto;

import lombok.Getter;
import lombok.Setter;
import pl.polsl.library.model.Member;

/**
 * The {@code LoginMember} class represents a DTO containing a library member and an associated JWT token.
 */
@Getter
@Setter
public class LoginMember {

    /**
     * The library member associated with the login.
     */
    private Member member;

    /**
     * The JWT token generated for the authenticated member.
     */
    private String jwt;

    /**
     * Default constructor for the LoginMember class.
     */
    public LoginMember() {
        super();
    }

    /**
     * Constructor for creating a LoginMember object with a specified member and JWT token.
     *
     * @param member The library member associated with the login.
     * @param jwt    The JWT token generated for the authenticated member.
     */
    public LoginMember(Member member, String jwt) {
        this.member = member;
        this.jwt = jwt;
    }
}
