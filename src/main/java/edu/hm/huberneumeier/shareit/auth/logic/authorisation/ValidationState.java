package edu.hm.huberneumeier.shareit.auth.logic.authorisation;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017-05-17
 */
public enum ValidationState {
    SUCCESS(200, "SUCCESS"),
    WRONG_INPUT(301, "WRONG_INPUT"),
    TOKEN_EXPIRED(301, "TOKEN_EXPIRED"),
    TOKEN_INVALID(301, "TOKEN_INVALID"),
    NO_PERMISSON(301, "NO_PERMISSION");

    private final int code;
    private final String name;

    ValidationState(int code, String name) {
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
