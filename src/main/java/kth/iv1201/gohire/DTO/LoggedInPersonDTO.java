package kth.iv1201.gohire.DTO;

/**
 * DTO containing information about a logged in persons <code>username</code> and <code>role</code>.
 */
public class LoggedInPersonDTO {
    private Integer id;
    private String username;
    private String role;

    /**
     * Creates an instance of a <code>LoggedInPersonDTO</code>.
     * @param id The id associated with the logged in person.
     * @param username The person's username.
     * @param role The person's role as a user of the application.
     */
    public LoggedInPersonDTO(Integer id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
