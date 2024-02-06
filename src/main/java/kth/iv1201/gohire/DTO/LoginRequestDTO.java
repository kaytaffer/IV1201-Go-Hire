package kth.iv1201.gohire.DTO;

/**
 * DTO containing information about a login request
 */
public class LoginRequestDTO {
    private final String username;
    private final String password;

    /**
     * Creates a new <code>LoginRequestDTO</code>
     * @param username the username
     * @param password the password
     */
    public LoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

