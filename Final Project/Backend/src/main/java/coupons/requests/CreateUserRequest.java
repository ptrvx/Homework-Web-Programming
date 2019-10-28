package coupons.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CreateUserRequest {

    @NotNull(message = "First Name must not be null")
    @NotBlank(message = "First Name must not be blank")
    private String firstName;

    @NotNull(message = "Last Name must not be null")
    @NotBlank(message = "Last Name must not be blank")
    private String lastName;

    @NotNull(message = "Privilege must not be null")
    @NotBlank(message = "Privilege must not be blank")
    @Pattern(regexp = "administrator|operator|ADMINISTRATOR|OPERATOR", message = "Privilege should be administrator or operator")
    private String privilege;

    @NotNull(message = "Username must not be null")
    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotNull(message = "Password must not be null")
    @NotBlank(message = "Password must not be blank")
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPrivilege() {
        return privilege;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
