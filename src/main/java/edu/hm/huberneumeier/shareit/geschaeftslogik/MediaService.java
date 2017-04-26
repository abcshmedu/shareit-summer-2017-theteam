package edu.hm.huberneumeier.shareit.geschaeftslogik;

import edu.hm.huberneumeier.shareit.fachklassen.medien.Book;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Disc;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Medium;

/**
 * Description.
 *
 * @author Andreas Neumeier
 * @version 2017-04-12
 */
public interface MediaService {

    public MediaServiceResult addBook(Book book);

    public MediaServiceResult addDisc(Disc disc);

    public Medium[] getBooks();

    public Medium[] getDiscs();

    public MediaServiceResult updateBook(Book book);

    public MediaServiceResult updateDisc(Disc disc);

    public Book getBook(String isbn);

    public Disc getDisc(String barcode);
}
