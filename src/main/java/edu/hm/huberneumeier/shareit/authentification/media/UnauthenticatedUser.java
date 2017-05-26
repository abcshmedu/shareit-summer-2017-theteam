package edu.hm.huberneumeier.shareit.authentification.media;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017-05-26
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
