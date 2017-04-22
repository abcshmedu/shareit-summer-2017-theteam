package edu.hm.huberneumeier.shareit.geschaeftslogik;

import edu.hm.huberneumeier.shareit.fachklassen.medien.Book;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Disc;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Medium;
import edu.hm.huberneumeier.shareit.geschaeftslogik.helpers.MediaSetUtils;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        mediaSet.add(new Book("Test", "test", "1234"));
    }

    @Override
    public MediaServiceResult addBook(Book book) {
        System.out.println(book);
        mediaSet.add(book);

        return MediaServiceResult.ACCEPTED;
    }

    @Override
    public MediaServiceResult addDisc(Disc disc) {
        mediaSet.add(disc);

        return null;
    }

    @Override
    public Medium[] getBooks() {
        System.out.println(mediaSet.toString());
        Medium[] mediaArray = MediaSetUtils.getMediaOfType(mediaSet, Book.class);
        return mediaSet.toArray(mediaArray);
    }

    @Override
    public Medium[] getDiscs() {
        Medium[] mediaArray = MediaSetUtils.getMediaOfType(mediaSet, Disc.class);
        return mediaSet.toArray(mediaArray);
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
