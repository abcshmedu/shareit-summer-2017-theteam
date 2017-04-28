package edu.hm.huberneumeier.shareit.geschaeftslogik;

import edu.hm.huberneumeier.shareit.fachklassen.medien.Book;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Disc;
import edu.hm.huberneumeier.shareit.fachklassen.medien.Medium;

/**
 * The media service interface defines all methods of a media service.
 *
 * @author Tobias Huber
 * @author Andreas Neumeier
 * @version 2017-04-26
 */
public interface MediaService {

    /**
     * Method to add a book to the media list.
     *
     * @param book the book which should be added.
     * @return the media service result.
     */
    public MediaServiceResult addBook(Book book);

    /**
     * Method to get a book from the media list.
     *
     * @param isbn ISBN of the book which is requested.
     * @return the requested book.
     */
    public Book getBook(String isbn);

    /**
     * Method to get all books from the media list.
     *
     * @return array of all books.
     */
    public Medium[] getBooks();

    /**
     * Method to update a book.
     *
     * @param isbn ISBN of the book which should be updated.
     * @param book the book-data with which the book should be updated.
     * @return the media service result.
     */
    public MediaServiceResult updateBook(String isbn, Book book);

    /**
     * Method to add a disc to the media list.
     *
     * @param disc the disc which should be added.
     * @return the media service result.
     */
    public MediaServiceResult addDisc(Disc disc);

    /**
     * Method to get a disc from the media list.
     *
     * @param barcode Barcode of the disc which is requested.
     * @return the requested disc.
     */
    public Disc getDisc(String barcode);

    /**
     * Method to get all discs from the media list.
     *
     * @return array of all discs.
     */
    public Medium[] getDiscs();

    /**
     * Method to update a disc.
     *
     * @param barcode Barcode of the disc which should be updated.
     * @param disc    the disc-data with which the disc should be updated.
     * @return the media service result.
     */
    public MediaServiceResult updateDisc(String barcode, Disc disc);
}
