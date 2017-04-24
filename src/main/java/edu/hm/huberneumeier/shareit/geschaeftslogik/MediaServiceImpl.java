package edu.hm.huberneumeier.shareit.geschaeftslogik;

import edu.hm.huberneumeier.shareit.fachklassen.medien.Book;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Disc;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Medium;
import edu.hm.huberneumeier.shareit.geschaeftslogik.helpers.MediaSetUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Description.
 *
 * @author Andreas Neumeier
 * @version 2017-04-12
 */
public class MediaServiceImpl implements MediaService {
    //hash set cos we need to be duplicate save
    Set<Medium> mediaSet = new HashSet<>();

    public MediaServiceImpl() {
        mediaSet.add(new Book("Test book", "test", "1234"));
        mediaSet.add(new Disc("8-5567-3", "test", 0,"test disc"));
    }

    @Override
    public MediaServiceResult addBook(Book book) {
        mediaSet.add(book);
        return MediaServiceResult.ACCEPTED;
    }

    @Override
    public MediaServiceResult addDisc(Disc disc) {
        mediaSet.add(disc);
        return MediaServiceResult.ACCEPTED;
    }

    @Override
    public Medium[] getBooks() {
        Medium[] mediaArray = MediaSetUtils.getMediaOfType(mediaSet, Book.class);
        return mediaArray;
    }

    @Override
    public Medium[] getDiscs() {
        Medium[] mediaArray = MediaSetUtils.getMediaOfType(mediaSet, Disc.class);
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
