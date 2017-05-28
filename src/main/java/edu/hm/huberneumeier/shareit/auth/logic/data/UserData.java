package edu.hm.huberneumeier.shareit.auth.logic.data;

import edu.hm.huberneumeier.shareit.auth.media.Token;
import edu.hm.huberneumeier.shareit.auth.media.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Mapping between users and there names & tokens.
 *
 * @author Andreas Neumeier
 * @author Tobias Huber
 * @version 28.05.2017
 */
public class UserData {
    private static Map<String, User> userList = User.getUserList();
    private static Map<Token, User> tokenUserMap = new HashMap<>();

    public static User getUser(String name) {
        return userList.get(name);
    }

    public static User getUser(Token token) {
        return tokenUserMap.get(token);
    }

    public static Boolean userExists(String name) {
        return userList.containsKey(name);
    }

    public static Boolean userExists(Token token) {
        return tokenUserMap.containsKey(token);
    }

    public static User removeUserToken(Token token) {
        return userList.get(token);
    }

    public static void setUserToken(User user, Token token) {
        user.setToken(token);
        tokenUserMap.put(token, user);
    }

    public static void setUserToken(String name, Token token) {
        setUserToken(getUser(name), token);
    }
}
