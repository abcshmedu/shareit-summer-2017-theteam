package edu.hm.huberneumeier.shareit.geschaeftslogik;

import edu.hm.huberneumeier.shareit.fachklassen.medien.Book;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Disc;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Medium;
import edu.hm.huberneumeier.shareit.geschaeftslogik.helpers.MediumListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description.
 *
 * @author Andreas Neumeier
 * @version 2017-04-12
 */
public class MediaServiceImpl implements MediaService {
    private final List<Medium> MEDIUM_LIST = new ArrayList<>();

    public MediaServiceImpl() {
        //MEDIUM_LIST.add(new Book("Test book", "test", "1234"));
        //MEDIUM_LIST.add(new Disc("8-5567-3", "test", 0, "test disc"));
    }

    @Override
    public MediaServiceResult addBook(Book book) {
        for (Medium medium : MediumListUtils.getMediaOfType(MEDIUM_LIST, Book.class)) {
            if (((Book) medium).getIsbn().equals(book.getIsbn()))
                return MediaServiceResult.BAD_REQUEST;
        }
        MEDIUM_LIST.add(book);
        return MediaServiceResult.ACCEPTED;
    }

    @Override
    public MediaServiceResult addDisc(Disc disc) {
        for (Medium medium : MediumListUtils.getMediaOfType(MEDIUM_LIST, Disc.class)) {
            if (((Disc) medium).getBarcode().equals(disc.getBarcode()))
                return MediaServiceResult.BAD_REQUEST;
        }
        MEDIUM_LIST.add(disc);
        return MediaServiceResult.ACCEPTED;
    }

    @Override
    public Medium[] getBooks() {
        Medium[] mediaArray = MediumListUtils.getMediaOfType(MEDIUM_LIST, Book.class);
        return mediaArray;
    }

    @Override
    public Medium[] getDiscs() {
        Medium[] mediaArray = MediumListUtils.getMediaOfType(MEDIUM_LIST, Disc.class);
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
