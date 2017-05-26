package edu.hm.huberneumeier.shareit.authentification.logic.authorisation;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017-05-17
 */
public class ValidationResult {
    private ValidationState validationState;
    private String message;

    public ValidationResult(ValidationState validationState, String message) {
        this.message = message;
        this.validationState = validationState;
    }

    public ValidationState getValidationState() {
        return validationState;
    }

    public String getMessage() {
        return message;
    }
}
