package coupons.models;

import coupons.enums.Privilege;

public class User {

    private String id;
    private String firstName;
    private String lastName;
    private Privilege privilege;
    private String username;
    private String password;

    public User(){}

    public User(String id, String firstName, String lastName, Privilege privilege, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.privilege = privilege;
        this.username = username;
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPrivilege(Privilege privilege) {
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

    public Privilege getPrivilege() {
        return privilege;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", privilege=" + privilege +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
