package edu.hm.huberneumeier.shareit.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Book;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Disc;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Medium;
import edu.hm.huberneumeier.shareit.geschaeftslogik.MediaService;
import edu.hm.huberneumeier.shareit.geschaeftslogik.MediaServiceImpl;
import edu.hm.huberneumeier.shareit.geschaeftslogik.MediaServiceResult;

import javax.ws.rs.Consumes;
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
    private MediaService mediaService = new MediaServiceImpl();

    public void clearMediaService() {
        mediaService = new MediaServiceImpl();
    }

    @POST
    @Path("books")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        MediaServiceResult result = mediaService.addBook(book);

        return Response.status(result.getStatus()).entity(jsonMapper(result)).build();
    }

    @GET
    @Path("books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {

        String jsonString = jsonMapper(mediaService.getBooks());

        return Response.status(200).entity(jsonString).build();
    }

    @PUT
    @Path("books/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(Book book) {
        MediaServiceResult result = mediaService.updateBook(book);

        return Response.status(result.getStatus()).build();
    }

    @PUT
    @Path("discs/{barcode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDisc(Disc disc) {
        MediaServiceResult result = mediaService.updateDisc(disc);

        return Response.status(result.getStatus()).build();
    }

    @POST
    @Path("discs")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDisc(Disc disc) {
        MediaServiceResult result = mediaService.addDisc(disc);

        return Response.status(result.getStatus()).build();
    }

    @GET
    @Path("discs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscs() {

        String jsonString = jsonMapper(mediaService.getDiscs());

        return Response.status(200).entity(jsonString).build();
    }
    
    @GET
    @Path("books/{isbn}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBookByISBN(@PathParam("isbn") String isbn) {

        ObjectMapper mapper = new ObjectMapper();

        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(mediaService.getBook(isbn));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Response.status(200).entity(jsonString).build();
    }

    @GET
    @Path("discs/{barcode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getDiscByBarcode(@PathParam("barcode") String barcode) {

        ObjectMapper mapper = new ObjectMapper();

        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(mediaService.getDisc(barcode));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Response.status(200).entity(jsonString).build();
    }

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
