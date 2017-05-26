package edu.hm.huberneumeier.shareit.authentification.logic.authentication;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017-05-26
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
