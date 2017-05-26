package edu.hm.huberneumeier.shareit.media.logic;

import edu.hm.huberneumeier.shareit.media.logic.helpers.Utils;
import edu.hm.huberneumeier.shareit.media.media.Book;
import edu.hm.huberneumeier.shareit.media.media.Disc;
import edu.hm.huberneumeier.shareit.media.media.Medium;
import uk.co.moreofless.ISBNValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a media service.
 * Methods to create, read and update different media types.
 *
 * @author Tobias Huber
 * @author Andreas Neumeier
 * @version 2017-04-26
 */
public class MediaServiceImpl implements MediaService {
    /**
     * List of all media items.
     */
    private final List<Medium> arrayList = new ArrayList<>();

    /**
     * Default constructor.
     */
    public MediaServiceImpl() {
    }

    @Override
    public MediaServiceResult addBook(Book book) {
        //TODO get the token from the user, this is just a "mock"
        //Token tokenFromUser = new Token();
        //final MediaServiceResult serviceResult = validateRequest(tokenFromUser, Authorisation.BOOK_CREATE);
//
        ////if result is not ok, there was a error, return and exit method
        //if (!serviceResult.equals(MediaServiceResult.OK))
        //    return serviceResult;


        //clear isbn from unnecessary characters like - or spaces
        book.clearISBN();

        //check if all parameters necessary are set
        if (!ISBNValidator.validateISBN13(book.getIsbn()) || book.getAuthor().isEmpty() || book.getTitle().isEmpty()) {
            return MediaServiceResult.BAD_REQUEST;
        }

        //check if book exists
        for (Medium medium : Utils.getMediaOfType(arrayList, Book.class)) {
            if (((Book) medium).getIsbn().equals(book.getIsbn())) {
                return MediaServiceResult.BAD_REQUEST;
            }
        }

        //add book if there wasn't an error
        arrayList.add(book);
        return MediaServiceResult.CREATED;
    }

    @Override
    public Book getBook(String isbn) {
        Medium[] mediaArray = Utils.getMediaOfType(arrayList, Book.class);

        for (Medium book : mediaArray) {
            Book current = (Book) book;
            if (current.getIsbn().equals(isbn)) {
                return current;
            }
        }
        return null;
    }

    @Override
    public Medium[] getBooks() {
        return Utils.getMediaOfType(arrayList, Book.class);
    }

    @Override
    public MediaServiceResult updateBook(String isbn, Book book) {
        //TODO get the token from the user, this is just a "mock"
        //Token tokenFromUser = new Token();
        //final MediaServiceResult serviceResult = validateRequest(tokenFromUser, Authorisation.BOOK_UPDATE);
//
        ////if result is not ok, there was a error, return and exit method
        //if (!serviceResult.equals(MediaServiceResult.OK))
        //    return serviceResult;


        //isbn cant be changed
        if (!isbn.equals(book.getIsbn())) {
            return MediaServiceResult.BAD_REQUEST;
        }

        //min one value must be set
        if (book.getTitle() == null && book.getAuthor() == null) {
            return MediaServiceResult.BAD_REQUEST;
        }

        Book result = getBookByIsbn(book.getIsbn());

        //if no book with isbn found end otherwise create the new book we will store
        Book newBook;
        if (result == null) {
            return MediaServiceResult.NOT_FOUND;
        } else if (book.getTitle() == null) {
            //not all information needed for updating so we need to set the missing values with the old once
            newBook = new Book(result.getTitle(), book.getAuthor(), book.getIsbn());
        } else if (book.getAuthor() == null) {
            //not all information needed for updating so we need to set the missing values with the old once
            newBook = new Book(book.getTitle(), result.getAuthor(), book.getIsbn());
        } else {
            newBook = book;
        }

        int id = getIdOfMedium(newBook.getIsbn());

        Book medium = (Book) arrayList.get(id);
        if (medium.getTitle().equals(newBook.getTitle()) && medium.getAuthor().equals(newBook.getAuthor())) {
            return MediaServiceResult.NOT_MODIFIED;
        }

        arrayList.set(id, newBook);
        return MediaServiceResult.ACCEPTED;
    }

    @Override
    public MediaServiceResult addDisc(Disc disc) {
        //TODO get the token from the user, this is just a "mock"
        //Token tokenFromUser = new Token();
        //final MediaServiceResult serviceResult = validateRequest(tokenFromUser, Authorisation.DISC_CREATE);
//
        ////if result is not ok, there was a error, return and exit method
        //if (!serviceResult.equals(MediaServiceResult.OK))
        //    return serviceResult;


        //clear barcode
        disc.clearBarcode();

        if (!Utils.validateBarcode(disc.getBarcode()) || disc.getDirector() == null || disc.getTitle() == null) {
            return MediaServiceResult.BAD_REQUEST;
        }

        for (Medium medium : Utils.getMediaOfType(arrayList, Disc.class)) {
            if (((Disc) medium).getBarcode().equals(disc.getBarcode())) {
                return MediaServiceResult.BAD_REQUEST;
            }
        }

        arrayList.add(disc);
        return MediaServiceResult.CREATED;
    }

    @Override
    public Disc getDisc(String barcode) {
        Medium[] mediaArray = Utils.getMediaOfType(arrayList, Disc.class);

        for (Medium disc : mediaArray) {
            Disc current = (Disc) disc;
            if (current.getBarcode().equals(barcode)) {
                return current;
            }
        }
        return null;
    }

    @Override
    public Medium[] getDiscs() {
        return Utils.getMediaOfType(arrayList, Disc.class);
    }

    @Override
    public MediaServiceResult updateDisc(String barcode, Disc disc) {
        //TODO get the token from the user, this is just a "mock"
        //Token tokenFromUser = new Token();
        //final MediaServiceResult serviceResult = validateRequest(tokenFromUser, Authorisation.DISC_UPDATE);
//
        ////if result is not ok, there was a error, return and exit method
        //if (!serviceResult.equals(MediaServiceResult.OK))
        //    return serviceResult;

        if (!barcode.equals(disc.getBarcode())) {
            return MediaServiceResult.BAD_REQUEST;
        }

        if (disc.getTitle() == null && disc.getDirector() == null && disc.getFsk() == null) {
            return MediaServiceResult.BAD_REQUEST;
        }

        Disc result = getDiscByBarcode(disc.getBarcode());

        //if no disc with barcode found end otherwise create the new disc we will store
        if (result == null) {
            return MediaServiceResult.NOT_FOUND;
        }

        //not all information needed for updating so we need to set the missing values with the old once
        Disc newDisc = disc;
        if (disc.getDirector() == null) {
            newDisc = new Disc(disc.getBarcode(), result.getDirector(), newDisc.getFsk(), newDisc.getTitle());
        }
        if (disc.getFsk() == null) {
            newDisc = new Disc(disc.getBarcode(), newDisc.getDirector(), result.getFsk(), newDisc.getTitle());
        }
        if (disc.getTitle() == null) {
            newDisc = new Disc(disc.getBarcode(), newDisc.getDirector(), newDisc.getFsk(), result.getTitle());
        }

        int id = getIdOfMedium(newDisc.getBarcode());

        Disc medium = (Disc) arrayList.get(id);
        if (medium.getTitle().equals(newDisc.getTitle()) && medium.getDirector().equals(newDisc.getDirector()) && medium.getFsk() == newDisc.getFsk()) {
            return MediaServiceResult.NOT_MODIFIED;
        }

        arrayList.set(id, newDisc);
        return MediaServiceResult.ACCEPTED;
    }

    /**
     * Search for a special book by isbn.
     *
     * @param isbn the isbn of the book to find
     * @return the book
     */
    private Book getBookByIsbn(String isbn) {
        //search for book with the given isbn -> result
        Medium[] mediaArray = Utils.getMediaOfType(arrayList, Book.class);
        Book result = null;
        for (Medium medium : mediaArray) {
            Book actual = (Book) medium;
            if (actual.getIsbn().equals(isbn)) {
                result = actual;
                break;
            }
        }
        return result;
    }

    /**
     * Search for a special disc by barcode.
     *
     * @param barcode the barcode of the disc to find
     * @return the barcode
     */
    private Disc getDiscByBarcode(String barcode) {
        Medium[] mediaArray = Utils.getMediaOfType(arrayList, Disc.class);
        Disc result = null;
        for (Medium medium : mediaArray) {
            Disc actual = (Disc) medium;
            if (actual.getBarcode().equals(barcode)) {
                result = actual;
                break;
            }
        }
        return result;
    }

    /**
     * Get the id of a medium in arrayList.
     *
     * @param identifier the identifier of the medium
     * @return the id of the medium
     */
    private int getIdOfMedium(String identifier) {
        //find out the id of the book to replace
        int id = 0;
        for (Medium medium : arrayList) {
            if (medium instanceof Book && ((Book) medium).getIsbn().equals(identifier)) {
                break;
            } else if (medium instanceof Disc && ((Disc) medium).getBarcode().equals(identifier)) {
                break;
            }
            id++;
        }
        return id;
    }
}
