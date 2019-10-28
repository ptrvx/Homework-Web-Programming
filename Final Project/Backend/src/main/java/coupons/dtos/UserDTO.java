package coupons.dtos;

public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String privilege;
    private String username;
    private String password;

    public void setId(String id) {
        this.id = id;
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

    public String getId() {
        return id;
    }

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
}
