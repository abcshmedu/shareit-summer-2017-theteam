package edu.hm.huberneumeier.shareit.resources;

import edu.hm.huberneumeier.shareit.fachklassen.medien.Book;
import edu.hm.huberneumeier.shareit.geschaeftslogik.MediaService;
import edu.hm.huberneumeier.shareit.geschaeftslogik.MediaServiceImpl;
import edu.hm.huberneumeier.shareit.geschaeftslogik.MediaServiceResult;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Description.
 *
 * @author Andreas Neumeier
 * @version 2017-04-12
 */

@Path("media")
public class MediaResource {
    private final MediaService mediaService;

    public MediaResource() {
        mediaService = new MediaServiceImpl();
    }

    @POST
    @Path("books")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        MediaServiceResult result = mediaService.addBook(book);

        return Response.status(result.getStatus()).build();
    }

    @GET
    @Path("books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {

        ObjectMapper mapper = new ObjectMapper();

        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(mediaService.getBooks());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Response.status(200).entity(jsonString).build();
    }

    @PUT
    @Path("books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(Book book) {
        return Response.status(200).build();
    }
}
