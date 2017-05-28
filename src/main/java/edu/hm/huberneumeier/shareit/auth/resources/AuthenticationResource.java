package edu.hm.huberneumeier.shareit.auth.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.huberneumeier.shareit.auth.logic.authentication.AuthenticationImpl;
import edu.hm.huberneumeier.shareit.auth.logic.authentication.AuthenticationResult;
import edu.hm.huberneumeier.shareit.auth.logic.authentication.AuthenticationState;
import edu.hm.huberneumeier.shareit.auth.media.jsonMappings.UnauthenticatedUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * API Where users can authenticate.
 *
 * @author Andreas Neumeier
 * @author Tobias Huber
 * @version 28.05.2017
 */
@Path("/")
public class AuthenticationResource {
    /**
     * Instance of AuthenticationResource Implementation.
     */
    private static final AuthenticationImpl AUTHENTICATION = new AuthenticationImpl();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(UnauthenticatedUser unathUser) {
        final AuthenticationResult result = AUTHENTICATION.authUser(unathUser.getUsername(), unathUser.getPassword());
        if (result.getAuthenticationState().equals(AuthenticationState.SUCCESS)) {
            return Response.status(Response.Status.OK).entity(jsonMapper(result.getToken())).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity(jsonMapper(result)).build();
        }
    }

    /**
     * Maps a object to a json string.
     *
     * @param object the object which should be mapped
     * @return the json string createds
     */
    private String jsonMapper(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
