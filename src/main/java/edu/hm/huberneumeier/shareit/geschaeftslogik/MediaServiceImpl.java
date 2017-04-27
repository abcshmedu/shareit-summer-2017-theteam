package edu.hm.huberneumeier.shareit.geschaeftslogik;

import edu.hm.huberneumeier.shareit.fachklassen.medien.Book;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Disc;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Medium;
import edu.hm.huberneumeier.shareit.geschaeftslogik.helpers.Utils;
import uk.co.moreofless.ISBNValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a media service.
 * Methods to create, read and update different media types.
 *
 * @author Tobias Huber, Andreas Neumeier
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
        //clear isbn from unnecessary characters like - or spaces
        book.clearISBN();

        //check if all parameters necessary are set
        if (!ISBNValidator.validateISBN13(book.getIsbn()) || book.getAuthor().isEmpty() || book.getTitle().isEmpty())
            return MediaServiceResult.BAD_REQUEST;

        //check if book exists
        for (Medium medium : Utils.getMediaOfType(arrayList, Book.class)) {
            if (((Book) medium).getIsbn().equals(book.getIsbn()))
                return MediaServiceResult.BAD_REQUEST;
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
            if (current.getIsbn().equals(isbn))
                return current;
        }
        return null;
    }

    @Override
    public Medium[] getBooks() {
        return Utils.getMediaOfType(arrayList, Book.class);
    }

    @Override
    public MediaServiceResult updateBook(String isbn, Book book) {
        //isbn cant be changed
        if (!isbn.equals(book.getIsbn()))
            return MediaServiceResult.BAD_REQUEST;

        //min one value must be set
        if (book.getTitle() == null && book.getAuthor() == null)
            return MediaServiceResult.BAD_REQUEST;

        //search for book with the given isbn -> result
        Medium[] mediaArray = Utils.getMediaOfType(arrayList, Book.class);
        Book result = null;
        for (Medium medium : mediaArray) {
            Book actual = (Book) medium;
            if (actual.getIsbn().equals(book.getIsbn())) {
                result = actual;
                break;
            }
        }

        //if no book with isbn found end otherwise create the new book we will store
        Book newBook;
        if (result == null)
            return MediaServiceResult.NOT_FOUND;
        else if (book.getTitle() == null)
            //not all information needed for updating so we need to set the missing values with the old once
            newBook = new Book(result.getTitle(), book.getAuthor(), book.getIsbn());
        else if (book.getAuthor() == null)
            //not all information needed for updating so we need to set the missing values with the old once
            newBook = new Book(book.getTitle(), result.getAuthor(), book.getIsbn());
        else
            newBook = book;

        //find out the id of the book to replace
        int id = 0;
        for (Medium medium : arrayList) {
            if (medium instanceof Book && ((Book) medium).getIsbn().equals(isbn))
                break;
            id++;
        }

        Book medium = (Book) arrayList.get(id);
        if (medium.getTitle().equals(newBook.getTitle()) && medium.getAuthor().equals(newBook.getAuthor()))
            return MediaServiceResult.NOT_MODIFIED;

        arrayList.set(id, newBook);
        return MediaServiceResult.ACCEPTED;
    }

    @Override
    public MediaServiceResult addDisc(Disc disc) {
        //clear barcode
        disc.clearBarcode();

        if (!Utils.validateBarcode(disc.getBarcode()) || disc.getDirector() == null || disc.getTitle() == null)
            return MediaServiceResult.BAD_REQUEST;

        for (Medium medium : Utils.getMediaOfType(arrayList, Disc.class)) {
            if (((Disc) medium).getBarcode().equals(disc.getBarcode()))
                return MediaServiceResult.BAD_REQUEST;
        }

        arrayList.add(disc);
        return MediaServiceResult.CREATED;
    }

    @Override
    public Disc getDisc(String barcode) {
        Medium[] mediaArray = Utils.getMediaOfType(arrayList, Disc.class);

        for (Medium disc : mediaArray) {
            Disc current = (Disc) disc;
            if (current.getBarcode().equals(barcode))
                return current;
        }
        return null;
    }

    @Override
    public Medium[] getDiscs() {
        return Utils.getMediaOfType(arrayList, Disc.class);
    }

    @Override
    public MediaServiceResult updateDisc(String barcode, Disc disc) {
        if (!barcode.equals(disc.getBarcode()))
            return MediaServiceResult.BAD_REQUEST;

        if (disc.getTitle().isEmpty() || disc.getDirector().isEmpty())
            return MediaServiceResult.BAD_REQUEST;

        Medium[] mediaArray = Utils.getMediaOfType(arrayList, Disc.class);
        Disc result = null;
        for (Medium medium : mediaArray) {
            Disc actual = (Disc) medium;
            if (actual.getBarcode().equals(disc.getBarcode())) {
                result = actual;
                break;
            }
        }

        //if no book with isbn found end otherwise create the new book we will store
        if (result == null)
            return MediaServiceResult.NOT_FOUND;

        //not all information needed for updating so we need to set the missing values with the old once
        Disc newDisc = disc;
        if (disc.getTitle() == null)
            newDisc = new Disc(result.getTitle(), newDisc.getDirector(), newDisc.getFsk(), newDisc.getBarcode());
        //Todo: ist int kann nicht als null übergeben werden was kommt dann wenn kein wert im json übertragen wird
        //if (disc.getFsk() == null)
        //    newDisc = new Disc(newDisc.getTitle(), newDisc.getDirector(), newDisc.getFsk(), newDisc.getBarcode());
        if (disc.getDirector() == null)
            newDisc = new Disc(newDisc.getTitle(), result.getDirector(), newDisc.getFsk(), newDisc.getBarcode());
        if (disc.getBarcode() == null)
            newDisc = new Disc(newDisc.getTitle(), newDisc.getDirector(), newDisc.getFsk(), result.getBarcode());

        int id = 0;
        for (Medium medium : arrayList) {
            if (!medium.equals(disc))
                break;
            id++;
        }

        Disc medium = (Disc) arrayList.get(id);
        if (medium.getTitle().equals(disc.getTitle()) && medium.getDirector().equals(disc.getDirector()) && medium.getFsk() == disc.getFsk())
            return MediaServiceResult.NOT_MODIFIED;

        arrayList.set(id, disc);
        return MediaServiceResult.ACCEPTED;
    }
}
