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
    //hash set cos we need to be duplicate save
    private final static List<Medium> MEDIUM_LIST = new ArrayList<>();

    public MediaServiceImpl() {
    }

    @Override
    public MediaServiceResult addBook(Book book) {
        if (!ISBNValidator.validateISBN13(book.getIsbn()))
            return MediaServiceResult.BAD_REQUEST;

        for (Medium medium : Utils.getMediaOfType(MEDIUM_LIST, Book.class)) {
            if (((Book) medium).getIsbn().equals(book.getIsbn()))
                return MediaServiceResult.BAD_REQUEST;
        }

        MEDIUM_LIST.add(book);
        return MediaServiceResult.ACCEPTED;
    }

    @Override
    public MediaServiceResult addDisc(Disc disc) {
        if (!Utils.validateBarcode(disc.getBarcode()))
            return MediaServiceResult.BAD_REQUEST;

        for (Medium medium : Utils.getMediaOfType(MEDIUM_LIST, Disc.class)) {
            if (((Disc) medium).getBarcode().equals(disc.getBarcode()))
                return MediaServiceResult.BAD_REQUEST;
        }

        MEDIUM_LIST.add(disc);
        return MediaServiceResult.ACCEPTED;
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
    public MediaServiceResult updateBook(Book book) {
        return null;
    }

    @Override
    public MediaServiceResult updateDisc(Disc disc) {
        return null;
    }
}
