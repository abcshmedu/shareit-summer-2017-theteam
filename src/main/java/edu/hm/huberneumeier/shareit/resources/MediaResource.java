package edu.hm.huberneumeier.shareit.resources;

import edu.hm.huberneumeier.shareit.fachklassen.medien.Book;
import org.eclipse.jetty.server.Response;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Description.
 *
 * @author Andreas Neumeier
 * @version 2017-04-12
 */

@Path("media")
public class MediaResource {
    public MediaResource() {
    }

    @POST
    @Path("books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        return null;
    }

    @GET
    @Path("books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        return Response.getResponse(null);
    }

    @PUT
    @Path("books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(Book book) {
        return null;
    }
}
