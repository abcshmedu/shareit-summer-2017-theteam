package hm.edu.huberneumeier.shareit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Book;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Disc;
import edu.hm.huberneumeier.shareit.geschaeftslogik.MediaServiceResult;
import edu.hm.huberneumeier.shareit.resources.MediaResource;
import org.junit.After;
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
    private static final String EXAMPLE_ISBN_2 = "9783827317100";
    private static final String EXAMPLE_ISBN_3 = "4003301018398";
    private static final String EXAMPLE_INCORRECT_ISBN = "0000000000004";
    private static final String EXAMPLE_BARCODE = "0000001234565";
    private static final String EXAMPLE_BARCODE_2 = "0012345612343";
    private static final String EXAMPLE_BARCODE_3 = "0000000000000";
    private static final String EXAMPLE_INCORRECT_BARCODE = "0000000000001";

    private static final Response RESPONSE_CREATE = Response.status(MediaServiceResult.CREATED.getCode()).entity(jsonMapper(MediaServiceResult.CREATED)).build();
    private static final Response RESPONSE_BAD_REQUEST = Response.status(MediaServiceResult.BAD_REQUEST.getCode()).entity(jsonMapper(MediaServiceResult.BAD_REQUEST)).build();
    private static final Response RESPONSE_ACCEPTED = Response.status(MediaServiceResult.ACCEPTED.getCode()).entity(jsonMapper(MediaServiceResult.ACCEPTED)).build();
    private static final Response RESPONSE_NOT_MODIFIED = Response.status(MediaServiceResult.NOT_MODIFIED.getCode()).entity(jsonMapper(MediaServiceResult.NOT_MODIFIED)).build();
    private static final Response RESPONSE_NOT_FOUND = Response.status(MediaServiceResult.NOT_FOUND.getCode()).entity(jsonMapper(MediaServiceResult.NOT_FOUND)).build();

    //Todo: Muss noch in den richtigen Ordner.
    @Test
    public void getBooksEmptyLibrary() throws Exception {
        mediaResource.clearMediaService();
        //Check response of get
        Response response = mediaResource.getBooks();
        Response emptyResponse = Response.status(200).entity("[]").build();
        Assert.assertEquals(emptyResponse.toString(), response.toString());
        Assert.assertEquals(emptyResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void getBooksOnlyDiscInLibrary() throws Exception {
        mediaResource.clearMediaService();
        mediaResource.createDisc(new Disc("Test", "Test", 0, "Test"));
        //Check response of get afterwards
        Response response = mediaResource.getBooks();
        Response emptyResponse = Response.status(200).entity("[]").build();
        Assert.assertEquals(emptyResponse.toString(), response.toString());
        Assert.assertEquals(emptyResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void createBooksSingleGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Check response of create
        Response createResponse = mediaResource.createBook(new Book("Test book", "test", EXAMPLE_ISBN));
        Assert.assertEquals(RESPONSE_CREATE.toString(), createResponse.toString());
        Assert.assertEquals(RESPONSE_CREATE.getEntity().toString(), createResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"Test book\",\"author\":\"test\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void createBooksSameISBNGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Check response of first create
        Response createResponse = mediaResource.createBook(new Book("Test book", "test", EXAMPLE_ISBN));
        Assert.assertEquals(RESPONSE_CREATE.toString(), createResponse.toString());
        Assert.assertEquals(RESPONSE_CREATE.getEntity().toString(), createResponse.getEntity().toString());
        //Check response of second create
        Response createResponseSecond = mediaResource.createBook(new Book("Test book", "test", EXAMPLE_ISBN));
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), createResponseSecond.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), createResponseSecond.getEntity().toString());
        //Check response of get afterwards if the book firstly put in is still there after a failed create
        Response response = mediaResource.getBooks();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"Test book\",\"author\":\"test\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void createBooksIncorrectIsbnGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Check response of first create
        Response createResponse = mediaResource.createBook(new Book("Test book", "test", EXAMPLE_INCORRECT_ISBN));
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), createResponse.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), createResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks();
        Response correctGetResponse = Response.status(200).entity("[]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksChangeTitleAuthorSetGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create book (checked earlier)
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN));
        //Check response of update
        Response updateResponse = mediaResource.updateBook(EXAMPLE_ISBN, new Book("changed", "test", EXAMPLE_ISBN));
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"changed\",\"author\":\"test\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksChangeAuthorTitleSetGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create book (checked earlier)
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN));
        //Check response of update
        Response updateResponse = mediaResource.updateBook(EXAMPLE_ISBN, new Book("test", "changed", EXAMPLE_ISBN));
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"test\",\"author\":\"changed\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksChangeAuthorAndTitleGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create book (checked earlier)
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN));
        //Check response of update
        Response updateResponse = mediaResource.updateBook(EXAMPLE_ISBN, new Book("changed", "changed", EXAMPLE_ISBN));
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"changed\",\"author\":\"changed\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksAuthorAndTitleNullGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create book (checked earlier)
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN));
        //Check response of update
        Response updateResponse = mediaResource.updateBook(EXAMPLE_ISBN, new Book(null, null, EXAMPLE_ISBN));
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"test\",\"author\":\"test\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksTitleNullGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create book (checked earlier)
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN));
        //Check response of update
        Response updateResponse = mediaResource.updateBook(EXAMPLE_ISBN, new Book(null, "changed", EXAMPLE_ISBN));
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"test\",\"author\":\"changed\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksAuthorNullGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create book (checked earlier)
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN));
        //Check response of update
        Response updateResponse = mediaResource.updateBook(EXAMPLE_ISBN, new Book("changed", null, EXAMPLE_ISBN));
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"changed\",\"author\":\"test\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksNonExistingBook() throws Exception {
        mediaResource.clearMediaService();
        //Check response of update
        Response updateResponse = mediaResource.updateBook(EXAMPLE_ISBN, new Book("changed", "something", EXAMPLE_ISBN));
        Assert.assertEquals(RESPONSE_NOT_FOUND.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_NOT_FOUND.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks();
        Response correctGetResponse = Response.status(200).entity("[]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksMultipleMediaExisting() throws Exception {
        mediaResource.clearMediaService();
        //Create book (checked earlier)
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN));
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN_2));
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN_3));
        //Check response of update
        mediaResource.updateBook(EXAMPLE_ISBN_3, new Book("changed", "changed", EXAMPLE_ISBN_3));
        mediaResource.updateBook(EXAMPLE_ISBN_2, new Book("changed", "changed", EXAMPLE_ISBN_2));
        mediaResource.updateBook(EXAMPLE_ISBN, new Book("changed", "changed", EXAMPLE_ISBN));
        //Check response of get afterwards
        Response response = mediaResource.getBooks();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"changed\",\"author\":\"changed\",\"isbn\":\"9781566199094\"},{\"title\":\"changed\",\"author\":\"changed\",\"isbn\":\"9783827317100\"},{\"title\":\"changed\",\"author\":\"changed\",\"isbn\":\"4003301018398\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksWrongIsbnGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create book (checked earlier)
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN));
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN_2));
        //Check response of update
        Response updateResponse = mediaResource.updateBook(EXAMPLE_ISBN, new Book("changed", "changed", EXAMPLE_ISBN_2));
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"test\",\"author\":\"test\",\"isbn\":\"9781566199094\"},{\"title\":\"test\",\"author\":\"test\",\"isbn\":\"9783827317100\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }
    
    //////////////// Disc tests /////////////////
    
    public void getDiscsEmptyLibrary() throws Exception {
        mediaResource.clearMediaService();
        //Check response of get
        Response response = mediaResource.getDiscs();
        Response emptyResponse = Response.status(200).entity("[]").build();
        Assert.assertEquals(emptyResponse.toString(), response.toString());
        Assert.assertEquals(emptyResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void getDiscsOnlyBookInLibrary() throws Exception {
        mediaResource.clearMediaService();
        mediaResource.createBook(new Book("Test", "Test", "test"));
        //Check response of get afterwards
        Response response = mediaResource.getDiscs();
        Response emptyResponse = Response.status(200).entity("[]").build();
        Assert.assertEquals(emptyResponse.toString(), response.toString());
        Assert.assertEquals(emptyResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void createDiscsSingleGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Check response of create
        Response createResponse = mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "Test Disc"));
        Assert.assertEquals(RESPONSE_CREATE.toString(), createResponse.toString());
        Assert.assertEquals(RESPONSE_CREATE.getEntity().toString(), createResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"Test Disc\",\"barcode\":\"0000001234565\",\"director\":\"test\",\"fsk\":0}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void createDiscsSameBarcodeGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Check response of first create
        Response createResponse = mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "Test Disc"));
        Assert.assertEquals(RESPONSE_CREATE.toString(), createResponse.toString());
        Assert.assertEquals(RESPONSE_CREATE.getEntity().toString(), createResponse.getEntity().toString());
        //Check response of second create
        Response createResponseSecond = mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "Test Disc"));
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), createResponseSecond.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), createResponseSecond.getEntity().toString());
        //Check response of get afterwards if the Disc firstly put in is still there after a failed create
        Response response = mediaResource.getDiscs();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"Test Disc\",\"barcode\":\"0000001234565\",\"director\":\"test\",\"fsk\":0}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void createDiscsIncorrectBarcodeGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Check response of first create
        Response createResponse = mediaResource.createDisc(new Disc(EXAMPLE_INCORRECT_BARCODE, "test", 0, "Test Disc"));
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), createResponse.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), createResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs();
        Response correctGetResponse = Response.status(200).entity("[]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscsChangeTitleAuthorSetGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "Test Disc"));
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_ISBN, new Disc(EXAMPLE_BARCODE, "test", 0, "changed"));
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"changed\",\"barcode\":\"0000001234565\",\"director\":\"test\",\"fsk\":0}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    /* TODO
    @Test
    public void updateDiscsChangeAuthorTitleSetGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc("test", "test", 0, EXAMPLE_ISBN));
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_ISBN, new Disc("test", "changed", 0, EXAMPLE_ISBN));
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"test\",\"author\":\"changed\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscsChangeAuthorAndTitleGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc("test", "test", 0, EXAMPLE_ISBN));
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_ISBN, new Disc("changed", "changed", 0, EXAMPLE_ISBN));
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"changed\",\"author\":\"changed\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscsAuthorAndTitleNullGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc("test", "test", 0, EXAMPLE_ISBN));
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_ISBN, new Disc(null, null, 0, EXAMPLE_ISBN));
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"test\",\"author\":\"test\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscsTitleNullGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc("test", "test", 0, EXAMPLE_ISBN));
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_ISBN, new Disc(null, "changed", 0, EXAMPLE_ISBN));
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"test\",\"author\":\"changed\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscsAuthorNullGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc("test", "test", 0, EXAMPLE_ISBN));
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_ISBN, new Disc("changed", null, 0, EXAMPLE_ISBN));
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"changed\",\"author\":\"test\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscsNonExistingDisc() throws Exception {
        mediaResource.clearMediaService();
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_ISBN, new Disc("changed", "something", 0, EXAMPLE_ISBN));
        Assert.assertEquals(RESPONSE_NOT_FOUND.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_NOT_FOUND.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs();
        Response correctGetResponse = Response.status(200).entity("[]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscsMultipleMediaExisting() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc("test", "test", 0, EXAMPLE_ISBN));
        mediaResource.createDisc(new Disc("test", "test",0, EXAMPLE_ISBN_2));
        mediaResource.createDisc(new Disc("test", "test", 0, EXAMPLE_ISBN_3));
        //Check response of update
        mediaResource.updateDisc(EXAMPLE_ISBN_3, new Disc("changed", "changed", 0, EXAMPLE_ISBN_3));
        mediaResource.updateDisc(EXAMPLE_ISBN_2, new Disc("changed", "changed", 0, EXAMPLE_ISBN_2));
        mediaResource.updateDisc(EXAMPLE_ISBN, new Disc("changed", "changed", 0, EXAMPLE_ISBN));
        //Check response of get afterwards
        Response response = mediaResource.getDiscs();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"changed\",\"author\":\"changed\",\"isbn\":\"9781566199094\"},{\"title\":\"changed\",\"author\":\"changed\",\"isbn\":\"9783827317100\"},{\"title\":\"changed\",\"author\":\"changed\",\"isbn\":\"4003301018398\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscsWrongIsbnGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc("test", "test", 0, EXAMPLE_ISBN));
        mediaResource.createDisc(new Disc("test", "test", 0, EXAMPLE_ISBN_2));
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_ISBN, new Disc("changed", "changed", 0, EXAMPLE_ISBN_2));
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs();
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"test\",\"author\":\"test\",\"isbn\":\"9781566199094\"},{\"title\":\"test\",\"author\":\"test\",\"isbn\":\"9783827317100\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    } */


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
