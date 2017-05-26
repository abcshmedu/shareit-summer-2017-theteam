package edu.hm.huberneumeier.shareit.media.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.huberneumeier.shareit.authentification.logic.authorisation.Authorisation;
import edu.hm.huberneumeier.shareit.authentification.logic.authorisation.AuthorisationImpl;
import edu.hm.huberneumeier.shareit.authentification.logic.authorisation.ValidationResult;
import edu.hm.huberneumeier.shareit.authentification.logic.authorisation.ValidationState;
import edu.hm.huberneumeier.shareit.media.logic.MediaService;
import edu.hm.huberneumeier.shareit.media.logic.MediaServiceImpl;
import edu.hm.huberneumeier.shareit.media.logic.MediaServiceResult;
import edu.hm.huberneumeier.shareit.media.media.Book;
import edu.hm.huberneumeier.shareit.media.media.Disc;

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

/**
 * Media Service is used to communicate directly with the frontend.
 *
 * @author Tobias Huber
 * @author Andreas Neumeier
 * @version 2017 -04-26
 */
@Path("media")
public class MediaResource {
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
        return getReturnResponse(authService.validate(token, Authorisation.BOOK_CREATE), mediaService.addBook(book));
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
        ValidationResult validationResult = authService.validate(token, Authorisation.BOOK_READ);
        Object result = validationResult;
        Response.Status response = Response.Status.UNAUTHORIZED;
        if (validationResult.getValidationState().equals(ValidationState.SUCCESS)) {
            response = Response.Status.OK;
            result = mediaService.getBook(isbn);
            if (result == null) {
                result = MediaServiceResult.NOT_FOUND;
                response = Response.Status.NOT_FOUND;
            }
        }

        return Response.status(response).entity(jsonMapper(result)).build();
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
        return getReturnResponse(authService.validate(token, Authorisation.BOOK_READ), mediaService.getBooks());
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
        return getReturnResponse(authService.validate(token, Authorisation.BOOK_UPDATE), mediaService.updateBook(isbn, book));
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
        return getReturnResponse(authService.validate(token, Authorisation.DISC_CREATE), mediaService.addDisc(disc));
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
        ValidationResult validationResult = authService.validate(token, Authorisation.DISC_READ);
        Object result = validationResult;
        Response.Status response = Response.Status.UNAUTHORIZED;
        if (validationResult.getValidationState().equals(ValidationState.SUCCESS)) {
            response = Response.Status.OK;
            result = mediaService.getDisc(barcode);
            if (result == null) {
                result = MediaServiceResult.NOT_FOUND;
                response = Response.Status.NOT_FOUND;
            }
        }

        return Response.status(response).entity(jsonMapper(result)).build();
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
        return getReturnResponse(authService.validate(token, Authorisation.DISC_READ), mediaService.getDiscs());
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
        return getReturnResponse(authService.validate(token, Authorisation.DISC_UPDATE), mediaService.updateDisc(barcode, disc));
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
     * @param validationResult the result of the validation of token and authorisation.
     * @param mediaServiceReturn the return object of the request on media service.
     * @return
     */
    private Response getReturnResponse(ValidationResult validationResult, Object mediaServiceReturn) {
        Object result = validationResult;
        Response.Status response = Response.Status.UNAUTHORIZED;
        if (validationResult.getValidationState().equals(ValidationState.SUCCESS)) {
            response = Response.Status.OK;
            result = mediaServiceReturn;
        }
        return Response.status(response).entity(jsonMapper(result)).build();
    }

}
