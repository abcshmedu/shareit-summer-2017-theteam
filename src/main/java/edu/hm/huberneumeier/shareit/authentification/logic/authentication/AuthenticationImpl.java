package edu.hm.huberneumeier.shareit.authentification.logic.authentication;

import edu.hm.huberneumeier.shareit.authentification.logic.data.UserData;
import edu.hm.huberneumeier.shareit.authentification.media.Token;
import edu.hm.huberneumeier.shareit.authentification.media.User;

/**
 * Description.
 *
 * @author andreas
 * @version 25.05.2017
 */
public class AuthenticationImpl implements AuthServiceExternal {

    @Override
    public AuthenticationResult authUser(String username, String password) {
        AuthenticationResult result;
        if (UserData.userExists(username)) {
            final User user = UserData.getUser(username);
            if (user.getPassword().equals(password)) {
                UserData.setUserToken(user, new Token());
                result = new AuthenticationResult(AuthenticationState.SUCCESS, user.getToken());
            } else {
                //password is incorrect -> but do not inform user, give no details about correct username
                result = new AuthenticationResult(AuthenticationState.WRONG_INPUT, "Unauthenticated - Your input was not correct.");
            }
        } else {
            //no user with the given username found -> but do not inform user
            result = new AuthenticationResult(AuthenticationState.WRONG_INPUT, "Unauthenticated - Your input was not correct.");
        }
        return result;
    }
}
