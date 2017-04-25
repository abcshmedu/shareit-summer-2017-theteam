package edu.hm.huberneumeier.shareit.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Book;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Disc;
import edu.hm.huberneumeier.shareit.geschaeftslogik.MediaService;
import edu.hm.huberneumeier.shareit.geschaeftslogik.MediaServiceImpl;
import edu.hm.huberneumeier.shareit.geschaeftslogik.MediaServiceResult;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
    private static final MediaService MEDIA_SERVICE = new MediaServiceImpl();

    public MediaResource() {
    }

    @POST
    @Path("books")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        MediaServiceResult result = MEDIA_SERVICE.addBook(book);

        return Response.status(result.getStatus()).build();
    }

    @GET
    @Path("books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {

        ObjectMapper mapper = new ObjectMapper();

        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(MEDIA_SERVICE.getBooks());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Response.status(200).entity(jsonString).build();
    }

    @PUT
    @Path("books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(Book book) {
        MediaServiceResult result = MEDIA_SERVICE.updateBook(book);

        return Response.status(result.getStatus()).build();
    }


    @POST
    @Path("discs")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDisc(Disc disc) {
        MediaServiceResult result = MEDIA_SERVICE.addDisc(disc);

        return Response.status(result.getStatus()).build();
    }

    @GET
    @Path("discs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscs() {

        ObjectMapper mapper = new ObjectMapper();

        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(MEDIA_SERVICE.getDiscs());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Response.status(200).entity(jsonString).build();
    }

    @PUT
    @Path("discs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDisc(Disc disc) {
        MediaServiceResult result = MEDIA_SERVICE.updateDisc(disc);

        return Response.status(result.getStatus()).build();
    }
}
