package edu.hm;

/**
 * Description.
 *
 * @author Andreas Neumeier
 * @version 2017-04-12
 * @created by Andreas Neumeier on 12.04.2017
 */
public class MediaServiceImpl implements MediaService {
    public MediaServiceImpl() {
    }

    @Override
    public MediaServiceResult addBook(Book book) {
        return null;
    }

    @Override
    public MediaServiceResult addDisc(Disc disc) {
        return null;
    }

    @Override
    public Medium[] getBooks() {
        return new Medium[0];
    }

    @Override
    public Medium[] getDiscs() {
        return new Medium[0];
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
