package kth.iv1201.gohire.DTO;

public class ApplicantDTO {
    private final String firstName;
    private final String lastName;
    private final String status;

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
