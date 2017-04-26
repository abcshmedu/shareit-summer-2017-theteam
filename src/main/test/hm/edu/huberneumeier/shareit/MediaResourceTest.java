package hm.edu.huberneumeier.shareit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Book;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Disc;
import edu.hm.huberneumeier.shareit.resources.MediaResource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Description.
 *
 * @author Andreas Neumeier
 * @version 2017-04-25
 */
public class MediaResourceTest {
    private final MediaResource mediaResource = new MediaResource();
    private static final String EXAMPLE_ISBN = "9781566199094";
    private static final String EXAMPLE_INCORRECT_ISBN = "0000000000004";

    //Todo: Muss noch in den richtigen Ordner.
    @Test
    public void getBooksEmptyLibrary() throws Exception {
        mediaResource.clearMediaService();
        Response response = mediaResource.getBooks();

        Response emptyResponse = Response.status(200).entity("[]").build();

        Assert.assertEquals(response.toString(), emptyResponse.toString());
        Assert.assertEquals(response.getEntity().toString(), emptyResponse.getEntity().toString());
    }

    @Test
    public void getBooksOnlyDiscsInLibrary() throws Exception {
        mediaResource.clearMediaService();
        mediaResource.createDisc(new Disc("Test", "Test", 0, "Test"));
        Response response = mediaResource.getBooks();

        Response emptyResponse = Response.status(200).entity("[]").build();

        Assert.assertEquals(emptyResponse.toString(), response.toString());
        Assert.assertEquals(emptyResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void createBooksSingle() throws Exception {
        mediaResource.clearMediaService();
        mediaResource.createBook(new Book("Test book", "test", EXAMPLE_ISBN));
        Response response = mediaResource.getBooks();

        Response correctResponse = Response.status(200).entity("[{\"title\":\"Test book\",\"author\":\"test\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();

        Assert.assertEquals(response.toString(), correctResponse.toString());
        Assert.assertEquals(response.getEntity().toString(), correctResponse.getEntity().toString());
    }

    @Test
    public void createBooksSameISBN() throws Exception {
        mediaResource.clearMediaService();
        mediaResource.createBook(new Book("Test book", "test", EXAMPLE_ISBN));
        mediaResource.createBook(new Book("Test book", "test", EXAMPLE_ISBN));
        mediaResource.createBook(new Book("Test book", "test", EXAMPLE_ISBN));
        Response response = mediaResource.getBooks();

        Response correctResponse = Response.status(200).entity("[{\"title\":\"Test book\",\"author\":\"test\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();

        Assert.assertEquals(response.toString(), correctResponse.toString());
        Assert.assertEquals(response.getEntity().toString(), correctResponse.getEntity().toString());
    }

    @Test
    public void createBooksIncorrectISBN() throws Exception {
        mediaResource.clearMediaService();
        mediaResource.createBook(new Book("Test book", "test", EXAMPLE_INCORRECT_ISBN));
        Response response = mediaResource.getBooks();

        Response correctResponse = Response.status(200).entity("[]").build();

        Assert.assertEquals(response.toString(), correctResponse.toString());
        Assert.assertEquals(response.getEntity().toString(), correctResponse.getEntity().toString());
    }

    @Test
    public void updateBooksNonExistingBook() throws Exception {
        mediaResource.clearMediaService();

    }

    @Test
    public void updateBooksSingleBook() throws Exception {
        mediaResource.clearMediaService();

    }
}
