package kth.iv1201.gohire.DTO;


/**
 * DTO containing information about an applicant creation request.
 */
public class CreateApplicantRequestDTO {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String personNumber;
    private final String username;
    private final String password;

    /**
     * Creates a <code>CreateApplicantRequestDTO</code> object with the provided information.
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param email the email of the user
     * @param personNumber the person number of the user
     * @param username the username of the user
     * @param password the password of the user
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
