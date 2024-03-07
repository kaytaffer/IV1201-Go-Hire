package kth.iv1201.gohire.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO containing information about an applicant creation request.
 */
public class CreateApplicantRequestDTO {

    @NotBlank(message = "Invalid First Name: can not be empty")
    @Size(max = 255, message = "Invalid First Name: must be 1-255 characters long.")
    private final String firstName;
    @NotBlank(message = "Invalid Last Name: can not be empty")
    @Size(max = 255, message = "Invalid Last Name: must be 1-255 characters long.")
    private final String lastName;
    @Email(message = "Should be an email")
    @NotBlank(message = "Invalid Email: can not be empty")
    @Size(max = 255, message = "Invalid Email: must be 1-255 characters long.")
    private final String email;
    @NotBlank(message = "Invalid Person Number: can not be empty")
    @Pattern(regexp = "\\d{8}-\\d{4}", message = "Invalid Person Number: Should have pattern YYYYMMDD-XXXX")
    private final String personNumber;
    @NotBlank(message = "Invalid Username: can not be empty")
    @Size(max = 255, message = "Invalid Username: must be 1-255 characters long.")
    private final String username;
    @NotBlank(message = "Invalid Password: can not be empty")
    @Size(max = 255, message = "Invalid Password: must be 1-255 characters long.")
    private final String password;

    /**
     * Creates a <code>CreateApplicantRequestDTO</code> object with the provided information.
     * @param firstName the first name of the user.
     * @param lastName the last name of the user.
     * @param email the email of the user.
     * @param personNumber the person number of the user.
     * @param username the username of the user.
     * @param password the password of the user.
     */
    public CreateApplicantRequestDTO(String firstName, String lastName, String email, String personNumber, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.personNumber = personNumber;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
