package edu.hm.huberneumeier.shareit.auth.logic.authentication;

/**
 * Authentication implementation interface.
 *
 * @author Tobias Huber
 * @version 28.05.2017
 */
public interface AuthServiceExternal {
    public AuthenticationResult authUser(String username, String password);
}
