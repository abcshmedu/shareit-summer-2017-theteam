package edu.hm.huberneumeier.shareit.auth.logic.authorisation;

import edu.hm.huberneumeier.shareit.auth.media.Authorisation;

/**
 * Interface for the internal authorisation service.
 *
 * @author Tobias Huber
 * @version 28.05.2017
 */
public interface AuthServiceInternal {
    public ValidationResult validate(String token, Authorisation authorisation);
}
