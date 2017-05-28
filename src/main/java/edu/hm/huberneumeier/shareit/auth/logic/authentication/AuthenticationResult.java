package edu.hm.huberneumeier.shareit.auth.logic.authentication;

import edu.hm.huberneumeier.shareit.auth.media.Token;

/**
 * Results of Authentications.
 *
 * @author Andreas Neumeier
 * @author Tobias Huber
 * @version 28.05.2017
 */
public class AuthenticationResult {
    private AuthenticationState authenticationState;
    private String message;
    private Token token;

    public AuthenticationResult(AuthenticationState authenticationState, String message) {
        this.message = message;
        this.authenticationState = authenticationState;
    }

    public AuthenticationResult(AuthenticationState authenticationState, Token token) {
        this.authenticationState = authenticationState;
        this.token = token;
    }

    public AuthenticationState getAuthenticationState() {
        return authenticationState;
    }

    public String getMessage() {
        return message;
    }

    public Token getToken() {
        return token;
    }
}
