package edu.hm.huberneumeier.shareit.authentification.logic.authorisation;

import edu.hm.huberneumeier.shareit.authentification.logic.authorisation.Authorisation;
import edu.hm.huberneumeier.shareit.authentification.logic.authorisation.ValidationResult;
import edu.hm.huberneumeier.shareit.authentification.media.Token;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017-05-17
 */
public interface AuthServiceInternal {
    public ValidationResult validate(String token, Authorisation authorisation);
}
