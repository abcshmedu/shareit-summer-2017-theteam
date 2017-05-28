package hm.edu.huberneumeier.shareit.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.hm.huberneumeier.shareit.auth.logic.authorisation.ValidationResult;
import edu.hm.huberneumeier.shareit.auth.logic.authorisation.ValidationState;
import edu.hm.huberneumeier.shareit.auth.media.Authorisation;
import edu.hm.huberneumeier.shareit.auth.media.jsonMappings.AuthorisationIDRequest;
import edu.hm.huberneumeier.shareit.auth.media.jsonMappings.UnauthenticatedUser;
import edu.hm.huberneumeier.shareit.auth.resources.AuthenticationResource;
import edu.hm.huberneumeier.shareit.auth.resources.AuthorisationResource;
import edu.hm.huberneumeier.shareit.media.resources.MediaResource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Description.
 *
 * @author andreas
 * @version 28.05.2017
 */
public class AuthorisationTests {

    private static final AuthorisationResource authorisationResource = new AuthorisationResource();
    private String validToken;

    @Before
    public void getToken() throws IOException {
        UnauthenticatedUser correctUser = new UnauthenticatedUser("admin", "123456");
        String json = (String) new AuthenticationResource().authenticateUser(correctUser).getEntity();
        ObjectNode object = new ObjectMapper().readValue(json, ObjectNode.class);
        JsonNode node = object.get("key");
        validToken = node.textValue();
    }

    @Test
    public void unothorized() throws IOException {
        Response response = authorisationResource.valideateRequest("invalideToken", new AuthorisationIDRequest(0));
        ObjectMapper objectMapper = new ObjectMapper();
        ValidationResult validationResult = objectMapper.readValue(response.getEntity().toString(), ValidationResult.class);

        Assert.assertEquals(ValidationState.TOKEN_INVALID, validationResult.getValidationState());
    }

    @Test
    public void noPermission() throws IOException {
        Response response = authorisationResource.valideateRequest(validToken, new AuthorisationIDRequest(0));
        ObjectMapper objectMapper = new ObjectMapper();
        ValidationResult validationResult = objectMapper.readValue(response.getEntity().toString(), ValidationResult.class);

        Assert.assertEquals(ValidationState.NO_PERMISSON, validationResult.getValidationState());
    }

    @Test
    public void permissionGranted() throws IOException {
        Response response = authorisationResource.valideateRequest(validToken, new AuthorisationIDRequest(1));
        ObjectMapper objectMapper = new ObjectMapper();
        ValidationResult validationResult = objectMapper.readValue(response.getEntity().toString(), ValidationResult.class);

        Assert.assertEquals(ValidationState.SUCCESS, validationResult.getValidationState());
    }

    private static String jsonMapper(Object object) {
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
