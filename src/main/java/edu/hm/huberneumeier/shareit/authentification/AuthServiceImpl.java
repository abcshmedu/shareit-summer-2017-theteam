package edu.hm.huberneumeier.shareit.authentification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017 -05-17
 */
public class AuthServiceImpl implements AuthServiceExternal, AuthServiceInternal {
    private Map<String, User> userList = new HashMap<>();
    private static Map<Token, User> tokenUserMap = new HashMap<>();


    /**
     * Instantiates a new Auth server.
     */
    public AuthServiceImpl() {
        userList = User.getUserList();
    }

    @Override
    public ValidationResult authUser(String username, String password) {
        ValidationResult result;
        if (userList.containsKey(username)) {
            final User user = userList.get(username);
            if (user.getPassword().equals(password)) {
                user.setToken(new Token());
                tokenUserMap.put(user.getToken(), user);
                result = new ValidationResult(ValidationState.SUCCESS, user.getToken());
            } else {
                //password is incorrect -> but do not inform user, give no details about correct username
                result = new ValidationResult(ValidationState.WRONG_INPUT, "Your input was not correct.");
            }
        } else {
            //no user with the given username found -> but do not inform user
            result = new ValidationResult(ValidationState.WRONG_INPUT, "Your input was not correct.");
        }
        return result;
    }

    @Override
    public ValidationResult validate(Token token, Authorisation authorisation) {
        ValidationResult result = validateToken(token);
        if (result.getValidationState().equals(ValidationState.SUCCESS)) {
            result = validateAuthorisation(tokenUserMap.get(token), authorisation);
        }
        return result;
    }

    private ValidationResult validateToken(Token token) {
        ValidationResult result;
        if (tokenUserMap.containsKey(token)) {
            if (token.getValidUntil() > System.currentTimeMillis()) {
                result = new ValidationResult(ValidationState.SUCCESS, "");
            } else {
                result = new ValidationResult(ValidationState.TOKEN_EXPIRED, "Your token is expired.");
                tokenUserMap.remove(token);
            }
        } else {
            result = new ValidationResult(ValidationState.TOKEN_INVALID, "Your token is invalid.");
        }
        return result;
    }

    private ValidationResult validateAuthorisation(User user, Authorisation authorisation) {
        ValidationResult result;
        List<Authorisation> auths = user.getAuthorisationGroup().getAuthorisations();
        if (auths.contains(authorisation)) {
            result = new ValidationResult(ValidationState.SUCCESS, "");
        } else {
            result = new ValidationResult(ValidationState.NO_PERMISSON, "You are not allowed to do this.");
        }
        return result;
    }
}
