package edu.hm.huberneumeier.shareit.resources;

import edu.hm.huberneumeier.shareit.fachklassen.medien.Book;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        return Response.status(200).entity("hello").build();
    }

    @PUT
    @Path("books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(Book book) {
        return null;
    }
}
