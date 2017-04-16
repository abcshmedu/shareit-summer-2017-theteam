package edu.hm.huberneumeier.geschaeftslogik;

import edu.hm.huberneumeier.fachklassen.medien.Book;
import edu.hm.huberneumeier.fachklassen.medien.Disc;
import edu.hm.huberneumeier.fachklassen.medien.Medium;

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
}
