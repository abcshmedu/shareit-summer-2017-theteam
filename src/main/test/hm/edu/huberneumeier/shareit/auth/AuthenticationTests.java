package hm.edu.huberneumeier.shareit.auth;

import edu.hm.huberneumeier.shareit.auth.media.jsonMappings.UnauthenticatedUser;
import edu.hm.huberneumeier.shareit.auth.resources.AuthenticationResource;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.regex.Pattern;

/**
 * Description.
 *
 * @author andreas
 * @version 26.05.2017
 */
public class AuthenticationTests {
    private final AuthenticationResource authenticationResourceResource = new AuthenticationResource();

    @Test
    public void authCorrectUser() {
        UnauthenticatedUser correctUser = new UnauthenticatedUser("user", "123456");
        //Check response
        Response response = authenticationResourceResource.authenticateUser(correctUser);
        Response expected = Response.status(200).build();
        Assert.assertEquals(expected.getStatus(), response.getStatus());
        Assert.assertTrue(Pattern.matches("\\{\"key\":\"[a-zA-Z0-9]+\",\"created\":[0-9]+,\"validUntil\":[0-9]+}", response.getEntity().toString()));
    }

    @Test
    public void authCorrectAdmin() {
        UnauthenticatedUser correctAdminUser = new UnauthenticatedUser("admin", "123456");
        //Check response
        Response response = authenticationResourceResource.authenticateUser(correctAdminUser);
        Response expected = Response.status(200).build();
        Assert.assertEquals(expected.getStatus(), response.getStatus());
        Assert.assertTrue(Pattern.matches("\\{\"key\":\"[a-zA-Z0-9]+\",\"created\":[0-9]+,\"validUntil\":[0-9]+}", response.getEntity().toString()));
    }

    @Test
    public void authIncorrectUser() {
        UnauthenticatedUser correctUser = new UnauthenticatedUser("user", "wrong!");
        //Check response
        Response response = authenticationResourceResource.authenticateUser(correctUser);
        Response expected = Response.status(401).build();
        Assert.assertEquals(expected.getStatus(), response.getStatus());
        Assert.assertEquals("{\"authenticationState\":\"WRONG_INPUT\",\"message\":\"Unauthenticated - Your input was not correct.\",\"token\":null}", response.getEntity().toString());
    }

    @Test
    public void authIncorrectAdmin() {
        UnauthenticatedUser correctAdminUser = new UnauthenticatedUser("admin", "wrong!");
        //Check response
        Response response = authenticationResourceResource.authenticateUser(correctAdminUser);
        Response expected = Response.status(401).build();
        Assert.assertEquals(expected.getStatus(), response.getStatus());
        Assert.assertEquals("{\"authenticationState\":\"WRONG_INPUT\",\"message\":\"Unauthenticated - Your input was not correct.\",\"token\":null}", response.getEntity().toString());
    }


}
