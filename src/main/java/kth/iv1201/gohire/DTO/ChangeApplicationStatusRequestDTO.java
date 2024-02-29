package kth.iv1201.gohire.DTO;

/**
 * DTO containing information about an applicant status change request.
 */
public class ChangeApplicationStatusRequestDTO {
    private final int id;
    private final String newStatus;
    private final String username;
    private final String password;

    /**
     * Creates a <code>ChangeApplicationStatusRequestDTO</code>
     * @param id the identifier of the applicant for which to change the status.
     * @param newStatus The new status of the application: 'accepted' or 'rejected'
     * @param username The username of the recruiter changing the status.
     * @param password The username of the recruiter changing the status.
     */
    public ChangeApplicationStatusRequestDTO(int id, String newStatus, String username, String password) {
        this.id = id;
        this.newStatus = newStatus;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
