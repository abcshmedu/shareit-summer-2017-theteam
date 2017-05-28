package edu.hm.huberneumeier.shareit.auth.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.huberneumeier.shareit.auth.media.Authorisation;
import edu.hm.huberneumeier.shareit.auth.logic.authorisation.AuthorisationImpl;
import edu.hm.huberneumeier.shareit.auth.logic.authorisation.ValidationResult;
import edu.hm.huberneumeier.shareit.auth.media.jsonMappings.AuthorisationIDRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * API for microservices to request if the user is allowed to perform a specific operation.
 *
 * @author Andreas Neumeier
 * @version 28.05.2017
 */
@Path("validate")
public class AuthorisationResource {
    /**
     * Instance of AuthenticationResource Implementation.
     */
    private static final AuthorisationImpl AUTHORISATION = new AuthorisationImpl();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response valideateRequest(@QueryParam("token") String token, AuthorisationIDRequest authorisationIDRequest) {
        ValidationResult validationResult = AUTHORISATION.validate(token, Authorisation.valueOf(authorisationIDRequest.getAuthorisationID()));
        return Response.status(validationResult.getValidationState().getCode()).entity(jsonMapper(validationResult)).build();
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
