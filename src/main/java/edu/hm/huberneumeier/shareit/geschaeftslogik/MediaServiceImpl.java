package edu.hm.huberneumeier.shareit.geschaeftslogik;

import edu.hm.huberneumeier.shareit.fachklassen.medien.Book;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Disc;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Medium;
import edu.hm.huberneumeier.shareit.geschaeftslogik.helpers.Utils;
import uk.co.moreofless.ISBNValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Description.
 *
 * @author Andreas Neumeier
 * @version 2017-04-12
 */
public class MediaServiceImpl implements MediaService {
    private static final List<Medium> MEDIUM_LIST = new ArrayList<>();

    public MediaServiceImpl() {
    }

    @Override
    public MediaServiceResult addBook(Book book) {
        //clear isbn
        book.clearISBN();

        if (!ISBNValidator.validateISBN13(book.getIsbn()) || book.getAuthor().isEmpty() || book.getTitle().isEmpty())
            return MediaServiceResult.BAD_REQUEST;

        for (Medium medium : Utils.getMediaOfType(MEDIUM_LIST, Book.class)) {
            if (((Book) medium).getIsbn().equals(book.getIsbn()))
                return MediaServiceResult.BAD_REQUEST;
        }


        MEDIUM_LIST.add(book);
        return MediaServiceResult.CREATED;
    }

    @Override
    public MediaServiceResult addDisc(Disc disc) {
        //clear barcode
        disc.clearBarcode();

        if (!Utils.validateBarcode(disc.getBarcode()) || disc.getDirector().isEmpty() || disc.getTitle().isEmpty())
            return MediaServiceResult.BAD_REQUEST;

        for (Medium medium : Utils.getMediaOfType(MEDIUM_LIST, Disc.class)) {
            if (((Disc) medium).getBarcode().equals(disc.getBarcode()))
                return MediaServiceResult.BAD_REQUEST;
        }

        MEDIUM_LIST.add(disc);
        return MediaServiceResult.CREATED;
    }

    @Override
    public Medium[] getBooks() {
        Medium[] mediaArray = Utils.getMediaOfType(MEDIUM_LIST, Book.class);
        return mediaArray;
    }

    @Override
    public Medium[] getDiscs() {
        Medium[] mediaArray = Utils.getMediaOfType(MEDIUM_LIST, Disc.class);
        return mediaArray;
    }

    @Override
    public MediaServiceResult updateBook(String isbn, Book book) {
        if (!isbn.equals(book.getIsbn()))
            return MediaServiceResult.BAD_REQUEST;

        if (book.getTitle().isEmpty() || book.getAuthor().isEmpty())
            return MediaServiceResult.BAD_REQUEST;

        Medium[] mediaArray = Utils.getMediaOfType(MEDIUM_LIST, Book.class);
        Book result = null;
        for (Medium medium : mediaArray) {
            Book actual = (Book) medium;
            if (actual.getIsbn().equals(book.getIsbn())) {
                result = actual;
                break;
            }
        }

        if (result == null)
            return MediaServiceResult.NOT_FOUND;

        int id = 0;
        for (Medium medium : MEDIUM_LIST) {
            if (medium.equals(book))
                break;
            id++;
        }

        Book medium = (Book) MEDIUM_LIST.get(id);
        if (medium.getTitle().equals(book.getTitle()) && medium.getAuthor().equals(book.getAuthor()))
            return MediaServiceResult.NOT_MODIFIED;

        MEDIUM_LIST.set(id, book);
        return MediaServiceResult.ACCEPTED;
    }

    @Override
    public MediaServiceResult updateDisc(String barcode, Disc disc) {
        if (!barcode.equals(disc.getBarcode()))
            return MediaServiceResult.BAD_REQUEST;

        if (disc.getTitle().isEmpty() || disc.getDirector().isEmpty())
            return MediaServiceResult.BAD_REQUEST;

        Medium[] mediaArray = Utils.getMediaOfType(MEDIUM_LIST, Disc.class);
        Disc result = null;
        for (Medium medium : mediaArray) {
            Disc actual = (Disc) medium;
            if (actual.getBarcode().equals(disc.getBarcode())) {
                result = actual;
                break;
            }
        }

        if (result == null)
            return MediaServiceResult.NOT_FOUND;

        int id = 0;
        for (Medium medium : MEDIUM_LIST) {
            if (!medium.equals(disc))
                break;
            id++;
        }

        MEDIUM_LIST.set(id, disc);
        return MediaServiceResult.ACCEPTED;
    }

    @Override
    public Book getBook(String isbn) {
        Medium[] mediaArray = Utils.getMediaOfType(MEDIUM_LIST, Book.class);

        for (Medium book : mediaArray) {
            Book current = (Book) book;
            if (current.getIsbn().equals(isbn))
                return current;
        }
        return null;
    }

    @Override
    public Disc getDisc(String barcode) {
        Medium[] mediaArray = Utils.getMediaOfType(MEDIUM_LIST, Disc.class);

        for (Medium disc : mediaArray) {
            Disc current = (Disc) disc;
            if (current.getBarcode().equals(barcode))
                return current;
        }
        return null;
    }
}
