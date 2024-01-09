package pl.polsl.library.model.dto;

/**
 * The {@code MemberProjection} interface represents a projection of member data with selected attributes.
 * It provides a subset of member information for simplified use cases.
 */
public interface MemberProjection {

    /**
     * Get the unique identifier for the member.
     *
     * @return The unique identifier for the member.
     */
    long getId();

    /**
     * Get the email address of the member.
     *
     * @return The email address of the member.
     */
    String getEmail();

    /**
     * Get the first name of the member.
     *
     * @return The first name of the member.
     */
    String getName();

    /**
     * Get the last name of the member.
     *
     * @return The last name of the member.
     */
    String getSurname();

    /**
     * Get the address of the member.
     *
     * @return The address of the member.
     */
    String getAddress();
}
