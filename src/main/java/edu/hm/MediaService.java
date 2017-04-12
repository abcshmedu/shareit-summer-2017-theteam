package edu.hm;

/**
 * Description.
 *
 * @author Andreas Neumeier
 * @version 2017-04-12
 * @created by Andreas Neumeier on 12.04.2017
 */
public interface MediaService {

    public MediaServiceResult addBook(Book book);

    public MediaServiceResult addDisc(Disc disc);

    public Medium[] getBooks();

    public Medium[] getDiscs();

    public MediaServiceResult updateBook(Book book);

    public MediaServiceResult updateDisc(Disc disc);
}
