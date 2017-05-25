package edu.hm.huberneumeier.shareit.authentification.logic.authentication;

import edu.hm.huberneumeier.shareit.authentification.logic.authorisation.ValidationResult;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017-05-17
 */
public interface AuthServiceExternal {
    public ValidationResult authUser(String username, String password);
}
