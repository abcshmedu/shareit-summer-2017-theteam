package edu.hm.huberneumeier.shareit.auth.logic.authorisation;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017-05-17
 */
public interface AuthServiceInternal {
    public ValidationResult validate(String token, Authorisation authorisation);
}
