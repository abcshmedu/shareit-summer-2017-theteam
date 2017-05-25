package edu.hm.huberneumeier.shareit.authentification.media;

import edu.hm.huberneumeier.shareit.authentification.logic.authorisation.AuthorisationGroup;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017-05-17
 */
public class User {
    private String username;
    private String password;
    private Date tokenExpires;
    private Token token;
    private AuthorisationGroup authorisationGroup;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        authorisationGroup = AuthorisationGroup.USERS;
    }

    public User(String username, String password, AuthorisationGroup authorisationGroup) {
        this.username = username;
        this.password = password;
        this.authorisationGroup = authorisationGroup;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public AuthorisationGroup getAuthorisationGroup() {
        return authorisationGroup;
    }

    public void setAuthorisationGroup(AuthorisationGroup authorisationGroup) {
        this.authorisationGroup = authorisationGroup;
    }

    public static Map<String, User> getUserList() {
        //create different users
        final Map<String, User> users = new HashMap();
        users.put("admin", new User("admin", "123456", AuthorisationGroup.ADMINS));
        users.put("user", new User("user", "123456"));
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
