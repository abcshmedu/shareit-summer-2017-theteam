package edu.hm.huberneumeier.shareit.auth.logic.authentication;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017-05-17
 */
public interface AuthServiceExternal {
    public AuthenticationResult authUser(String username, String password);
}
