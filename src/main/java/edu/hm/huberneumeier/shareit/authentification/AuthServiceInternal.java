package edu.hm.huberneumeier.shareit.authentification;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017-05-17
 */
public interface AuthServiceInternal {
    public ValidationResult validate(Token token, Authorisation authorisation);
}
