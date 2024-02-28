package kth.iv1201.gohire.DTO;

/**
 * DTO containing information about an application.
 */
public class ApplicantDTO {
    private final String firstName;
    private final String lastName;
    private final String status;

    /**
     * Creates a new <code>ApplicantDTO</code>
     * @param firstName the first name of the applicant
     * @param lastName the last name of the applicant
     * @param status the status of the application
     */
    public ApplicantDTO(String firstName, String lastName, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getStatus() {
        return status;
    }
}