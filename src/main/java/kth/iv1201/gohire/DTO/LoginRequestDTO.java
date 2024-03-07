package kth.iv1201.gohire.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO containing information about a login request.
 */
public class LoginRequestDTO {
    @NotBlank(message = "Invalid username: Username can not be empty")
    @Size(max = 255, message = "Invalid username: Username must be 1-255 characters long.")
    private final String username;
    @NotBlank(message = "Invalid password: Password can not be empty")
    @Size(max = 255, message = "Invalid password: Password must be 1-255 characters long.")
    private final String password;

    /**
     * Creates a <code>LoginRequestDTO</code>
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

