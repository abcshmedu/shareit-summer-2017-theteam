package edu.hm.huberneumeier.shareit.authentification.logic.authorisation;

import edu.hm.huberneumeier.shareit.authentification.logic.data.UserData;
import edu.hm.huberneumeier.shareit.authentification.media.Token;
import edu.hm.huberneumeier.shareit.authentification.media.User;

import java.util.List;

/**
 * Description.
 *
 * @author andreas
 * @version 25.05.2017
 */
public class AuthorisationImpl implements AuthServiceInternal {

    @Override
    public ValidationResult validate(String tokenKey, Authorisation authorisation) {
        Token token = Token.getTokenToKey(tokenKey);
        ValidationResult result = validateToken(token);
        if (result.getValidationState().equals(ValidationState.SUCCESS)) {
            result = validateAuthorisation(UserData.getUser(token), authorisation);
        }
        return result;
    }

    private ValidationResult validateToken(Token token) {
        ValidationResult result;
        if (token != null && UserData.userExists(token)) {
            if (token.getValidUntil() > System.currentTimeMillis()) {
                result = new ValidationResult(ValidationState.SUCCESS, "");
            } else {
                result = new ValidationResult(ValidationState.TOKEN_EXPIRED, "Unauthorised - Your token is expired.");
                UserData.removeUserToken(token);
            }
        } else {
            result = new ValidationResult(ValidationState.TOKEN_INVALID, "Unauthorised - Your token is invalid.");
        }
        return result;
    }

    private ValidationResult validateAuthorisation(User user, Authorisation authorisation) {
        ValidationResult result;
        List<Authorisation> auths = user.getAuthorisationGroup().getAuthorisations();
        if (auths.contains(authorisation)) {
            result = new ValidationResult(ValidationState.SUCCESS, "");
        } else {
            result = new ValidationResult(ValidationState.NO_PERMISSON, "Unauthorised - You are not allowed to do this.");
        }
        return result;
    }
}
