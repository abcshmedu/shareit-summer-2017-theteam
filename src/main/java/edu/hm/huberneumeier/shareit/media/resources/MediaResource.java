package edu.hm.huberneumeier.shareit.media.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.huberneumeier.shareit.auth.media.Authorisation;
import edu.hm.huberneumeier.shareit.auth.logic.authorisation.AuthorisationImpl;
import edu.hm.huberneumeier.shareit.auth.logic.authorisation.ValidationResult;
import edu.hm.huberneumeier.shareit.auth.logic.authorisation.ValidationState;
import edu.hm.huberneumeier.shareit.auth.media.jsonMappings.AuthorisationIDRequest;
import edu.hm.huberneumeier.shareit.auth.resources.AuthorisationResource;
import edu.hm.huberneumeier.shareit.media.logic.MediaService;
import edu.hm.huberneumeier.shareit.media.logic.MediaServiceImpl;
import edu.hm.huberneumeier.shareit.media.logic.MediaServiceResult;
import edu.hm.huberneumeier.shareit.media.media.Book;
import edu.hm.huberneumeier.shareit.media.media.Disc;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.function.Supplier;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Media Service is used to communicate directly with the frontend.
 *
 * @author Tobias Huber
 * @author Andreas Neumeier
 * @version 2017 -04-26
 */
@Path("/")
public class MediaResource {
    private static final String AUTH_SERVER_URL = "http://localhost:8082/shareit/auth/validate";
    private static Boolean isUnitTesting = false;
    private static final int RESPONSE_CODE_OK = 200;
    /**
     * Instance of the Media Service Implementation.
     */
    private static MediaService mediaService = new MediaServiceImpl();
    private static AuthorisationImpl authService = new AuthorisationImpl();

    /**
     * Method to create a fresh media service.
     */
    public void clearMediaService() {
        mediaService = new MediaServiceImpl();
    }

    /**
     * Create a new book and return the response.
     *
     * @param book  Book to add to the media list.
     * @param token the token
     * @return The response got from media service.
     */
    @POST
    @Path("books")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book, @QueryParam("token") String token) {
        return getReturnResponse(
                authService.validate(token, Authorisation.BOOK_CREATE),
                () -> {
                    MediaServiceResult result = mediaService.addBook(book);
                    return Response.status(result.getStatus()).entity(jsonMapper(result)).build();
                }
        );
    }

    /**
     * Get the book with the given isbn.
     *
     * @param isbn  ISBN of book.
     * @param token the token
     * @return The response got from media service.
     */
    @GET
    @Path("books/{isbn}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBookByISBN(@PathParam("isbn") String isbn, @QueryParam("token") String token) {
        return getReturnResponse(
                authService.validate(token, Authorisation.BOOK_READ),
                () -> {
                    Object result = mediaService.getBook(isbn);
                    Response.Status response = Response.Status.OK;
                    if (result == null) {
                        result = MediaServiceResult.NOT_FOUND;
                        response = Response.Status.NOT_FOUND;
                    }
                    return Response.status(response).entity(jsonMapper(result)).build();
                }
        );
    }

    /**
     * Get all books.
     *
     * @param token the token
     * @return The response got from media service.
     */
    @GET
    @Path("books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks(@QueryParam("token") String token) {
        return getReturnResponse(
                Authorisation.BOOK_READ,
                token,
                () -> Response.status(RESPONSE_CODE_OK).entity(jsonMapper(mediaService.getBooks())).build()
        );
    }

    /**
     * Update a special book.
     *
     * @param isbn  ISBN of the book which should be updated.
     * @param book  New book-data.
     * @param token the token
     * @return The response got from media service.
     */
    @PUT
    @Path("books/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("isbn") String isbn, Book book, @QueryParam("token") String token) {
        return getReturnResponse(
                authService.validate(token, Authorisation.BOOK_UPDATE),
                () -> {
                    MediaServiceResult result = mediaService.updateBook(isbn, book);
                    return Response.status(result.getStatus()).entity(jsonMapper(result)).build();
                }
        );
    }

    /**
     * Create a new disc and return the response.
     *
     * @param disc  Disc to add to the media list.
     * @param token the token
     * @return The response got from media service.
     */
    @POST
    @Path("discs")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDisc(Disc disc, @QueryParam("token") String token) {
        return getReturnResponse(
                authService.validate(token, Authorisation.BOOK_CREATE),
                () -> {
                    MediaServiceResult result = mediaService.addDisc(disc);
                    return Response.status(result.getStatus()).entity(jsonMapper(result)).build();
                }
        );
    }

    /**
     * Get the disc with the given barcode.
     *
     * @param barcode Barcode of disc.
     * @param token   the token
     * @return The response got from media service.
     */
    @GET
    @Path("discs/{barcode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getDiscByBarcode(@PathParam("barcode") String barcode, @QueryParam("token") String token) {
        return getReturnResponse(
                authService.validate(token, Authorisation.DISC_READ),
                () -> {
                    Object result = mediaService.getDisc(barcode);
                    Response.Status response = Response.Status.OK;
                    if (result == null) {
                        result = MediaServiceResult.NOT_FOUND;
                        response = Response.Status.NOT_FOUND;
                    }
                    return Response.status(response).entity(jsonMapper(result)).build();
                }
        );
    }

    /**
     * Get all discs.
     *
     * @param token the token
     * @return The response got from media service.
     */
    @GET
    @Path("discs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscs(@QueryParam("token") String token) {
        return getReturnResponse(
                authService.validate(token, Authorisation.DISC_READ),
                () -> Response.status(RESPONSE_CODE_OK).entity(jsonMapper(mediaService.getDiscs())).build()
        );
    }

    /**
     * Update a special disc.
     *
     * @param barcode Barcode of the disc which should be updated.
     * @param disc    New disc-data.
     * @param token   the token
     * @return The response got from media service.
     */
    @PUT
    @Path("discs/{barcode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDisc(@PathParam("barcode") String barcode, Disc disc, @QueryParam("token") String token) {
        return getReturnResponse(
                authService.validate(token, Authorisation.DISC_UPDATE),
                () -> {
                    MediaServiceResult result = mediaService.updateDisc(barcode, disc);
                    return Response.status(result.getStatus()).entity(jsonMapper(result)).build();
                }
        );
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

    /**
     * Produce the response which is return by the methods above.
     *
     * @param validationResult  the result of the validation of token and authorisation.
     * @param responsePredicate what to do when the validation is positive.
     * @return The result of the request.
     */
    private Response getReturnResponse(ValidationResult validationResult, Supplier<Response> responsePredicate) {
        if (validationResult.getValidationState() == ValidationState.SUCCESS) {
            return responsePredicate.get();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(jsonMapper(validationResult)).build();
    }

    private Response getReturnResponse(Authorisation authorisation, String token, Supplier<Response> responsePredicate) {
        try {
            ValidationResult validationResult = null;
            if(!isUnitTesting)
                validationResult = sendValidationPost(token, jsonMapper(new AuthorisationIDRequest(authorisation.getId())));
            else{
                ObjectMapper objectMapper = new ObjectMapper();
                validationResult = objectMapper.readValue("{\"validationState\":\"SUCCESS\",\"message\":\"\"}", ValidationResult.class);
            }

            if (validationResult.getValidationState() == ValidationState.SUCCESS) {
                return responsePredicate.get();
            }
            return Response.status(Response.Status.UNAUTHORIZED).entity(jsonMapper(validationResult)).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_GATEWAY).entity("Validation service not reachable!").build();
        }
    }

    /**
     * Request the authorisation service if the user is allowed to perform the requested action.
     *
     * @param token The users token.
     * @param json  The requested operation wrapped as small json object.
     * @return  The validation result.
     * @throws Exception
     */
    private ValidationResult sendValidationPost(String token, String json) throws Exception {

        URL obj = new URL(AUTH_SERVER_URL+"/?token="+token);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty( "Content-Type", MediaType.APPLICATION_JSON);
        con.setRequestProperty( "charset", "utf-8");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(json.getBytes("UTF-8"));
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        con.disconnect();

        ObjectMapper objectMapper = new ObjectMapper();
        ValidationResult validationResult = objectMapper.readValue(response.toString(), ValidationResult.class);

        return validationResult;
    }

    public static void setIsUnitTesting(Boolean isUnitTesting) {
        MediaResource.isUnitTesting = isUnitTesting;
    }
}
