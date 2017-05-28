package hm.edu.huberneumeier.shareit.auth;

import edu.hm.huberneumeier.shareit.auth.logic.authentication.AuthenticationState;
import edu.hm.huberneumeier.shareit.auth.logic.data.UserData;
import edu.hm.huberneumeier.shareit.auth.media.Authorisation;
import edu.hm.huberneumeier.shareit.auth.media.AuthorisationGroup;
import edu.hm.huberneumeier.shareit.auth.media.Token;
import edu.hm.huberneumeier.shareit.auth.media.User;
import edu.hm.huberneumeier.shareit.auth.media.jsonMappings.AuthorisationIDRequest;
import edu.hm.huberneumeier.shareit.auth.media.jsonMappings.UnauthenticatedUser;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description.
 *
 * @author andreas
 * @version 28.05.2017
 */
public class MediaDataTests {

    @Test
    public void authorisationIDRequest() {
        Integer id = 1;
        AuthorisationIDRequest authorisationIDRequest = new AuthorisationIDRequest(id);
        Assert.assertEquals(id,authorisationIDRequest.getAuthorisationID());
    }

    @Test
    public void authorisationIDRequestDefaultCont() {
        Integer id = 1;
        AuthorisationIDRequest authorisationIDRequest = new AuthorisationIDRequest();
        Assert.assertEquals(null,authorisationIDRequest.getAuthorisationID());
    }

    @Test
    public void unauthenticatedUser() {
        UnauthenticatedUser unauthenticatedUser = new UnauthenticatedUser("user", "password");
        Assert.assertEquals("password",unauthenticatedUser.getPassword());
        Assert.assertEquals("user",unauthenticatedUser.getUsername());
    }

    @Test
    public void unauthenticatedUserDefaultConst() {
        UnauthenticatedUser unauthenticatedUser = new UnauthenticatedUser();
        Assert.assertEquals(null,unauthenticatedUser.getPassword());
        Assert.assertEquals(null,unauthenticatedUser.getUsername());
    }

    @Test
    public void authorisation() {
        Assert.assertEquals(Authorisation.BOOK_CREATE.getDescription(),"Your are allowed to create a new book");
        Assert.assertEquals(Authorisation.BOOK_CREATE.getId(),1);
        Assert.assertEquals(Authorisation.BOOK_CREATE.getName(),"media.book.create");
    }

    @Test
    public void authorisationGroup() {
        Assert.assertEquals(AuthorisationGroup.ADMINS.getName(),"admins");
        Assert.assertTrue(AuthorisationGroup.ADMINS.getAuthorisations().contains(Authorisation.BOOK_CREATE));
    }

    @Test
    public void token() {
        Token toke = new Token();
        Assert.assertTrue(toke.getKey().matches("[A-Za-z0-9]+"));
        Assert.assertTrue(toke.getCreated() >= System.currentTimeMillis() && toke.getCreated() <= System.currentTimeMillis()+10000);
        Assert.assertTrue(toke.getValidUntil() > toke.getCreated());
    }

    @Test
    public void user() {
        User user = new User("testuser", "password");
        Assert.assertEquals(AuthorisationGroup.USERS,user.getAuthorisationGroup());
        Assert.assertEquals("password",user.getPassword());
        Assert.assertEquals("testuser",user.getUsername());
        user.setAuthorisationGroup(AuthorisationGroup.ADMINS);

        User user2 = new User("testuser", "password", AuthorisationGroup.ADMINS);
        Assert.assertTrue(user.equals(user2));
    }

    @Test
    public void authenticationState() {
        AuthenticationState authenticationState = AuthenticationState.SUCCESS;
        Assert.assertEquals("SUCCESS",authenticationState.getName());
        Assert.assertEquals("200",authenticationState.getCode());
    }
}
