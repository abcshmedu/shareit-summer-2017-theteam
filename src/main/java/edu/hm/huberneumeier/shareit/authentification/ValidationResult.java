package edu.hm.huberneumeier.shareit.authentification;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017-05-17
 */
public class ValidationResult {
    private ValidationState validationState;
    private String message;
    private Token token;

    public ValidationResult(ValidationState validationState, String message) {
        this.message = message;
        this.validationState = validationState;
    }

    public ValidationResult(ValidationState validationState, Token token) {
        this.validationState = validationState;
        this.token = token;
    }

    public ValidationState getValidationState() {
        return validationState;
    }

    public String getMessage() {
        return message;
    }
}
