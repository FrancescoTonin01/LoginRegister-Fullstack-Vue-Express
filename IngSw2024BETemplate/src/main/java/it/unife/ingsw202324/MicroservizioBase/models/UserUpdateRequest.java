package it.unife.ingsw202324.MicroservizioBase.models;

public class UserUpdateRequest {
    private String username;
    private String password;
    private String oldUsername;

    // Default constructor
    public UserUpdateRequest() {}

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for oldUsername
    public String getOldUsername() {
        return oldUsername;
    }

    // Setter for oldUsername
    public void setOldUsername(String oldUsername) {
        this.oldUsername = oldUsername;
    }
}