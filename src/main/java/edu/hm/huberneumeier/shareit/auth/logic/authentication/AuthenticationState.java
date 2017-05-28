package edu.hm.huberneumeier.shareit.auth.logic.authentication;

/**
 * States which can be hold by an AuthenticationResult.
 *
 * @author Andreas Neumeier
 * @author Tobias Huber
 * @version 25.05.2017
 */
public enum AuthenticationState {
    SUCCESS(200, "SUCCESS"),
    WRONG_INPUT(301, "WRONG_INPUT");

    private final int code;
    private final String name;

    AuthenticationState(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
