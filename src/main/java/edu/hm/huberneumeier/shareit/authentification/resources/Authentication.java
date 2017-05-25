package edu.hm.huberneumeier.shareit.authentification.resources;

import edu.hm.huberneumeier.shareit.authentification.logic.authentication.AuthServiceExternal;
import edu.hm.huberneumeier.shareit.authentification.logic.authentication.AuthenticationImpl;
import edu.hm.huberneumeier.shareit.authentification.logic.authorisation.ValidationResult;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Description.
 *
 * @author andreas
 * @version 25.05.2017
 */
@Path("auth")
public class Authentication {
    /**
     * Instance of the Media Service Implementation.
     */
    private static AuthServiceExternal authServiceExternal = new AuthenticationImpl();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response token(String username, String password) {
        ValidationResult result = authServiceExternal.authUser(username, password);
        //Return the token in the message (or an error)
        return Response.status(result.getValidationState().getCode()).entity(result.getMessage()).build();
    }
}
