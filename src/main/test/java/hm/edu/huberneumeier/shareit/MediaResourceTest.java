package hm.edu.huberneumeier.shareit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.huberneumeier.shareit.media.media.Book;
import edu.hm.huberneumeier.shareit.media.media.Copy;
import edu.hm.huberneumeier.shareit.media.media.Disc;
import edu.hm.huberneumeier.shareit.media.media.Medium;
import edu.hm.huberneumeier.shareit.media.logic.MediaServiceResult;
import edu.hm.huberneumeier.shareit.media.logic.helpers.Utils;
import edu.hm.huberneumeier.shareit.media.resources.MediaResource;
import org.junit.Assert;
import org.junit.Test;
import uk.co.moreofless.ISBNValidator;

import javax.ws.rs.core.Response;

/**
 * Tests for MediaResources (book and disc).
 *
 * @author Tobias Huber
 * @author Andreas Neumeier
 * @version 2017-04-26
 */
public class MediaResourceTest {
    private final MediaResource mediaResource = new MediaResource();
    private static final String EXAMPLE_ISBN = "9781566199094";
    private static final String EXAMPLE_ISBN_2 = "9783827317100";
    private static final String EXAMPLE_ISBN_3 = "4003301018398";
    private static final String EXAMPLE_INCORRECT_ISBN = "0000000000004";
    private static final String EXAMPLE_INCORRECT_ISBN_2 = "00000%000x004";
    private static final String EXAMPLE_BARCODE = "0000001234565";
    private static final String EXAMPLE_BARCODE_2 = "0012345612343";
    private static final String EXAMPLE_BARCODE_3 = "0000000000000";
    private static final String EXAMPLE_INCORRECT_BARCODE = "0000000000001";
    private static final String EXAMPLE_INCORRECT_BARCODE_2 = "00000%000x001";

    private static final Response RESPONSE_CREATE = Response.status(MediaServiceResult.CREATED.getCode()).entity(jsonMapper(MediaServiceResult.CREATED)).build();
    private static final Response RESPONSE_BAD_REQUEST = Response.status(MediaServiceResult.BAD_REQUEST.getCode()).entity(jsonMapper(MediaServiceResult.BAD_REQUEST)).build();
    private static final Response RESPONSE_ACCEPTED = Response.status(MediaServiceResult.ACCEPTED.getCode()).entity(jsonMapper(MediaServiceResult.ACCEPTED)).build();
    private static final Response RESPONSE_NOT_MODIFIED = Response.status(MediaServiceResult.NOT_MODIFIED.getCode()).entity(jsonMapper(MediaServiceResult.NOT_MODIFIED)).build();
    private static final Response RESPONSE_NOT_FOUND = Response.status(MediaServiceResult.NOT_FOUND.getCode()).entity(jsonMapper(MediaServiceResult.NOT_FOUND)).build();

    private static final String VALID_TOKEN = "";

    @Test
    public void checkValidateIsbn() throws Exception {
        Assert.assertFalse(ISBNValidator.validateISBN13(null));
    }

    @Test
    public void getBooksEmptyLibrary() throws Exception {
        mediaResource.clearMediaService();
        //Check response of get
        Response response = mediaResource.getBooks(VALID_TOKEN);
        Response emptyResponse = Response.status(200).entity("[]").build();
        Assert.assertEquals(emptyResponse.toString(), response.toString());
        Assert.assertEquals(emptyResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void getBooksOnlyDiscInLibrary() throws Exception {
        mediaResource.clearMediaService();
        mediaResource.createDisc(new Disc("Test", "Test", 0, "Test"), VALID_TOKEN);
        //Check response of get afterwards
        Response response = mediaResource.getBooks(VALID_TOKEN);
        Response emptyResponse = Response.status(200).entity("[]").build();
        Assert.assertEquals(emptyResponse.toString(), response.toString());
        Assert.assertEquals(emptyResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void getBooksNotExisting() throws Exception {
        mediaResource.clearMediaService();
        //Check response of get afterwards
        Response response = mediaResource.getBookByISBN(EXAMPLE_ISBN, VALID_TOKEN);
        Assert.assertEquals(RESPONSE_NOT_FOUND.toString(), response.toString());
        Assert.assertEquals(RESPONSE_NOT_FOUND.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void createBooksSingleGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Check response of create
        Response createResponse = mediaResource.createBook(new Book("Test book", "test", EXAMPLE_ISBN), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_CREATE.toString(), createResponse.toString());
        Assert.assertEquals(RESPONSE_CREATE.getEntity().toString(), createResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"Test book\",\"author\":\"test\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void createBooksSingleGetAfterwardsByISBN() throws Exception {
        mediaResource.clearMediaService();
        //Check response of create
        Response createResponse = mediaResource.createBook(new Book("Test book", "test", EXAMPLE_ISBN), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_CREATE.toString(), createResponse.toString());
        Assert.assertEquals(RESPONSE_CREATE.getEntity().toString(), createResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBookByISBN(EXAMPLE_ISBN, VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("{\"title\":\"Test book\",\"author\":\"test\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void createBooksSameISBNGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Check response of first create
        Response createResponse = mediaResource.createBook(new Book("Test book", "test", EXAMPLE_ISBN), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_CREATE.toString(), createResponse.toString());
        Assert.assertEquals(RESPONSE_CREATE.getEntity().toString(), createResponse.getEntity().toString());
        //Check response of second create
        Response createResponseSecond = mediaResource.createBook(new Book("Test book", "test", EXAMPLE_ISBN), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), createResponseSecond.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), createResponseSecond.getEntity().toString());
        //Check response of get afterwards if the book firstly put in is still there after a failed create
        Response response = mediaResource.getBooks(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"Test book\",\"author\":\"test\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void createBookWithIncorrectIsbn() throws Exception {
        mediaResource.clearMediaService();
        //Check response of first create
        Response createResponse = mediaResource.createBook(new Book("Test book", "test", EXAMPLE_INCORRECT_ISBN_2), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), createResponse.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), createResponse.getEntity().toString());
    }

    @Test
    public void createBooksIncorrectIsbnGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Check response of first create
        Response createResponse = mediaResource.createBook(new Book("Test book", "test", EXAMPLE_INCORRECT_ISBN), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), createResponse.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), createResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksChangeTitleAuthorSetGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create book (checked earlier)
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateBook(EXAMPLE_ISBN, new Book("changed", "test", EXAMPLE_ISBN), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"changed\",\"author\":\"test\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksChangeAuthorTitleSetGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create book (checked earlier)
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateBook(EXAMPLE_ISBN, new Book("test", "changed", EXAMPLE_ISBN), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"test\",\"author\":\"changed\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksChangeAuthorAndTitleGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create book (checked earlier)
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateBook(EXAMPLE_ISBN, new Book("changed", "changed", EXAMPLE_ISBN), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"changed\",\"author\":\"changed\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksAuthorAndTitleNullGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create book (checked earlier)
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateBook(EXAMPLE_ISBN, new Book(null, null, EXAMPLE_ISBN), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"test\",\"author\":\"test\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksTitleNullGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create book (checked earlier)
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateBook(EXAMPLE_ISBN, new Book(null, "changed", EXAMPLE_ISBN), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"test\",\"author\":\"changed\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksAuthorNullGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create book (checked earlier)
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateBook(EXAMPLE_ISBN, new Book("changed", null, EXAMPLE_ISBN), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"changed\",\"author\":\"test\",\"isbn\":\"" + EXAMPLE_ISBN + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksNonExistingBook() throws Exception {
        mediaResource.clearMediaService();
        //Check response of update
        Response updateResponse = mediaResource.updateBook(EXAMPLE_ISBN, new Book("changed", "something", EXAMPLE_ISBN), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_NOT_FOUND.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_NOT_FOUND.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksMultipleMediaExisting() throws Exception {
        mediaResource.clearMediaService();
        //Create book (checked earlier)
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN), VALID_TOKEN);
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN_2), VALID_TOKEN);
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN_3), VALID_TOKEN);
        //Check response of update
        mediaResource.updateBook(EXAMPLE_ISBN_3, new Book("changed", "changed", EXAMPLE_ISBN_3), VALID_TOKEN);
        mediaResource.updateBook(EXAMPLE_ISBN_2, new Book("changed", "changed", EXAMPLE_ISBN_2), VALID_TOKEN);
        mediaResource.updateBook(EXAMPLE_ISBN, new Book("changed", "changed", EXAMPLE_ISBN), VALID_TOKEN);
        //Check response of get afterwards
        Response response = mediaResource.getBooks(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"changed\",\"author\":\"changed\",\"isbn\":\"" + EXAMPLE_ISBN + "\"},{\"title\":\"changed\",\"author\":\"changed\",\"isbn\":\"" + EXAMPLE_ISBN_2 + "\"},{\"title\":\"changed\",\"author\":\"changed\",\"isbn\":\"" + EXAMPLE_ISBN_3 + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBooksWrongIsbnGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create book (checked earlier)
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN), VALID_TOKEN);
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN_2), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateBook(EXAMPLE_ISBN, new Book("changed", "changed", EXAMPLE_ISBN_2), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getBooks(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"test\",\"author\":\"test\",\"isbn\":\"" + EXAMPLE_ISBN + "\"},{\"title\":\"test\",\"author\":\"test\",\"isbn\":\"" + EXAMPLE_ISBN_2 + "\"}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateBookWithNoChangesGetNotModifiedError() throws Exception {
        mediaResource.clearMediaService();
        //Create Book (checked earlier)
        mediaResource.createBook(new Book("test", "test", EXAMPLE_ISBN), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateBook(EXAMPLE_ISBN, new Book("test", "test", EXAMPLE_ISBN), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_NOT_MODIFIED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_NOT_MODIFIED.getEntity().toString(), updateResponse.getEntity().toString());
    }

    //////////////// Disc tests /////////////////
    @Test
    public void checkBarcodeValidator() throws Exception {
        Assert.assertFalse(Utils.validateBarcode(null));
    }

    @Test
    public void getDiscsEmptyLibrary() throws Exception {
        mediaResource.clearMediaService();
        //Check response of get
        Response response = mediaResource.getDiscs(VALID_TOKEN);
        Response emptyResponse = Response.status(200).entity("[]").build();
        Assert.assertEquals(emptyResponse.toString(), response.toString());
        Assert.assertEquals(emptyResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void getDiscsOnlyBookInLibrary() throws Exception {
        mediaResource.clearMediaService();
        mediaResource.createBook(new Book("Test", "Test", "test"), VALID_TOKEN);
        //Check response of get afterwards
        Response response = mediaResource.getDiscs(VALID_TOKEN);
        Response emptyResponse = Response.status(200).entity("[]").build();
        Assert.assertEquals(emptyResponse.toString(), response.toString());
        Assert.assertEquals(emptyResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void getDiscNotExisting() throws Exception {
        mediaResource.clearMediaService();
        //Check response of get afterwards
        Response response = mediaResource.getDiscByBarcode(EXAMPLE_BARCODE, VALID_TOKEN);
        Assert.assertEquals(RESPONSE_NOT_FOUND.toString(), response.toString());
        Assert.assertEquals(RESPONSE_NOT_FOUND.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void createDiscsSingleGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Check response of create
        Response createResponse = mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "Test Disc"), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_CREATE.toString(), createResponse.toString());
        Assert.assertEquals(RESPONSE_CREATE.getEntity().toString(), createResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"Test Disc\",\"barcode\":\"" + EXAMPLE_BARCODE + "\",\"director\":\"test\",\"fsk\":0}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void createDiscsSingleGetAfterwardsByBarcode() throws Exception {
        mediaResource.clearMediaService();
        //Check response of create
        Response createResponse = mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "Test Disc"), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_CREATE.toString(), createResponse.toString());
        Assert.assertEquals(RESPONSE_CREATE.getEntity().toString(), createResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscByBarcode(EXAMPLE_BARCODE, VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("{\"title\":\"Test Disc\",\"barcode\":\"" + EXAMPLE_BARCODE + "\",\"director\":\"test\",\"fsk\":0}").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void createDiscsSameBarcodeGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Check response of first create
        Response createResponse = mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "Test Disc"), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_CREATE.toString(), createResponse.toString());
        Assert.assertEquals(RESPONSE_CREATE.getEntity().toString(), createResponse.getEntity().toString());
        //Check response of second create
        Response createResponseSecond = mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "Test Disc"), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), createResponseSecond.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), createResponseSecond.getEntity().toString());
        //Check response of get afterwards if the Disc firstly put in is still there after a failed create
        Response response = mediaResource.getDiscs(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"Test Disc\",\"barcode\":\"" + EXAMPLE_BARCODE + "\",\"director\":\"test\",\"fsk\":0}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void createDiscWithIncorrectIsbn() throws Exception {
        mediaResource.clearMediaService();
        //Check response of first create
        Response createResponse = mediaResource.createDisc(new Disc(EXAMPLE_INCORRECT_BARCODE_2, "test", 0, "Test Disc"), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), createResponse.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), createResponse.getEntity().toString());
    }

    @Test
    public void createDiscsIncorrectBarcodeGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Check response of first create
        Response createResponse = mediaResource.createDisc(new Disc(EXAMPLE_INCORRECT_BARCODE, "test", 0, "Test Disc"), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), createResponse.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), createResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscsChangeTitleAuthorSetGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "Test Disc"), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_BARCODE, new Disc(EXAMPLE_BARCODE, "test", 0, "changed"), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"changed\",\"barcode\":\"" + EXAMPLE_BARCODE + "\",\"director\":\"test\",\"fsk\":0}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }


    @Test
    public void updateDiscsChangeAuthorTitleSetGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "test"), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_BARCODE, new Disc(EXAMPLE_BARCODE, "changed", 0, "test"), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"test\",\"barcode\":\"" + EXAMPLE_BARCODE + "\",\"director\":\"changed\",\"fsk\":0}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscsChangeAuthorAndTitleGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "test"), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_BARCODE, new Disc(EXAMPLE_BARCODE, "changed", 0, "changed"), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"changed\",\"barcode\":\"" + EXAMPLE_BARCODE + "\",\"director\":\"changed\",\"fsk\":0}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscsAuthorAndTitleNullGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "test"), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_ISBN, new Disc(EXAMPLE_BARCODE, null, 0, null), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"test\",\"barcode\":\"" + EXAMPLE_BARCODE + "\",\"director\":\"test\",\"fsk\":0}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscsTitleNullGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "test"), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_BARCODE, new Disc(EXAMPLE_BARCODE, "changed", 0, null), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"test\",\"barcode\":\"" + EXAMPLE_BARCODE + "\",\"director\":\"changed\",\"fsk\":0}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscsAuthorNullGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "test"), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_BARCODE, new Disc(EXAMPLE_BARCODE, null, 0, "changed"), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"changed\",\"barcode\":\"" + EXAMPLE_BARCODE + "\",\"director\":\"test\",\"fsk\":0}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscsNonExistingDisc() throws Exception {
        mediaResource.clearMediaService();
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_BARCODE, new Disc(EXAMPLE_BARCODE, "something", 0, "changed"), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_NOT_FOUND.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_NOT_FOUND.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscsMultipleMediaExisting() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "test"), VALID_TOKEN);
        mediaResource.createDisc(new Disc(EXAMPLE_BARCODE_2, "test", 0, "test"), VALID_TOKEN);
        mediaResource.createDisc(new Disc(EXAMPLE_BARCODE_3, "test", 0, "test"), VALID_TOKEN);
        //Check response of update
        mediaResource.updateDisc(EXAMPLE_BARCODE_3, new Disc(EXAMPLE_BARCODE_3, "changed", 0, "changed"), VALID_TOKEN);
        mediaResource.updateDisc(EXAMPLE_BARCODE_2, new Disc(EXAMPLE_BARCODE_2, "changed", 0, "changed"), VALID_TOKEN);
        mediaResource.updateDisc(EXAMPLE_BARCODE, new Disc(EXAMPLE_BARCODE, "changed", 0, "changed"), VALID_TOKEN);
        //Check response of get afterwards
        Response response = mediaResource.getDiscs(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"changed\",\"barcode\":\"" + EXAMPLE_BARCODE + "\",\"director\":\"changed\",\"fsk\":0},{\"title\":\"changed\",\"barcode\":\"" + EXAMPLE_BARCODE_2 + "\",\"director\":\"changed\",\"fsk\":0},{\"title\":\"changed\",\"barcode\":\"" + EXAMPLE_BARCODE_3 + "\",\"director\":\"changed\",\"fsk\":0}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscsWrongBarcodeGetAfterwards() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "test"), VALID_TOKEN);
        mediaResource.createDisc(new Disc(EXAMPLE_BARCODE_2, "test", 0, "test"), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_BARCODE, new Disc(EXAMPLE_BARCODE_2, "changed", 0, "changed"), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"test\",\"barcode\":\"" + EXAMPLE_BARCODE + "\",\"director\":\"test\",\"fsk\":0},{\"title\":\"test\",\"barcode\":\"" + EXAMPLE_BARCODE_2 + "\",\"director\":\"test\",\"fsk\":0}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscWithNoChangesGetNotModifiedError() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "test"), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_BARCODE, new Disc(EXAMPLE_BARCODE, "test", 0, "test"), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_NOT_MODIFIED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_NOT_MODIFIED.getEntity().toString(), updateResponse.getEntity().toString());
    }

    @Test
    public void updateDiscWithNoFskChange() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "test"), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_BARCODE, new Disc(EXAMPLE_BARCODE, "test123", null, "test123"), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_ACCEPTED.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_ACCEPTED.getEntity().toString(), updateResponse.getEntity().toString());
        //Check response of get afterwards
        Response response = mediaResource.getDiscs(VALID_TOKEN);
        Response correctGetResponse = Response.status(200).entity("[{\"title\":\"test123\",\"barcode\":\"" + EXAMPLE_BARCODE + "\",\"director\":\"test123\",\"fsk\":0}]").build();
        Assert.assertEquals(correctGetResponse.toString(), response.toString());
        Assert.assertEquals(correctGetResponse.getEntity().toString(), response.getEntity().toString());
    }

    @Test
    public void updateDiscWithAllValuesNull() throws Exception {
        mediaResource.clearMediaService();
        //Create Disc (checked earlier)
        mediaResource.createDisc(new Disc(EXAMPLE_BARCODE, "test", 0, "test"), VALID_TOKEN);
        //Check response of update
        Response updateResponse = mediaResource.updateDisc(EXAMPLE_BARCODE, new Disc(EXAMPLE_BARCODE, null, null, null), VALID_TOKEN);
        Assert.assertEquals(RESPONSE_BAD_REQUEST.toString(), updateResponse.toString());
        Assert.assertEquals(RESPONSE_BAD_REQUEST.getEntity().toString(), updateResponse.getEntity().toString());
    }

    @Test
    public void checkBooksEquals() throws Exception {
        Book book = new Book("test", "test", EXAMPLE_ISBN);
        Book book2 = new Book("test", "test", EXAMPLE_ISBN);
        Assert.assertTrue(book.equals(book2));
    }

    @Test
    public void checkDiscsEquals() throws Exception {
        Disc disc = new Disc(EXAMPLE_BARCODE, "test", 0, "test");
        Disc disc2 = new Disc(EXAMPLE_BARCODE, "test", 0, "test");
        Assert.assertTrue(disc.equals(disc2));
    }

    @Test
    public void checkCopy() throws Exception {
        Disc disc = new Disc(EXAMPLE_BARCODE, "test", 0, "test");
        Copy copy = new Copy("owner", disc);
        Copy copy2 = new Copy("owner", disc);

        Assert.assertTrue(copy.equals(copy2));
    }

    @Test
    public void checkEmptyConstructors() throws Exception {
        Medium medium = new Medium();
        Medium medium2 = new Medium();
        Assert.assertTrue(medium.equals(medium2));
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
