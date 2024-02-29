package kth.iv1201.gohire.DTO;

/**
 * DTO containing information about an application.
 */
public class ApplicantDTO {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String status;

    /**
     * Creates a new <code>ApplicantDTO</code>
     * @param id the unique identifier of the applicant.
     * @param firstName the first name of the applicant.
     * @param lastName the last name of the applicant.
     * @param status the status of the application.
     */
    public ApplicantDTO(int id, String firstName, String lastName, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public int getId() {
        return id;
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
