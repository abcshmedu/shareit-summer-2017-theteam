package edu.hm.huberneumeier.shareit.auth.logic.authorisation;

/**
 * Results given back from validations.
 *
 * @author Tobias Huber
 * @version 28.05.2017
 */
public class ValidationResult {
    private ValidationState validationState;
    private String message;

    public ValidationResult(){}

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
