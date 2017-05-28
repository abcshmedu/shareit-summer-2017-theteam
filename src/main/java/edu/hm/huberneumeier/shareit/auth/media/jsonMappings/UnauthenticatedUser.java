package edu.hm.huberneumeier.shareit.auth.media.jsonMappings;

/**
 * Used to map json authentication requests from the authentication api.
 *
 * @author Tobias Huber
 * @version 28.05.2017
 */
public class UnauthenticatedUser {
    private String username;
    private String password;

    public UnauthenticatedUser(){

    }

    public UnauthenticatedUser(String username, String password) {
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
