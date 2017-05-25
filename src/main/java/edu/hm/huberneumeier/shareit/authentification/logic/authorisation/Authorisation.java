package edu.hm.huberneumeier.shareit.authentification.logic.authorisation;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017-05-17
 */
public enum Authorisation {
    /**
     * Media service results.
     */
    BOOK_CREATE(1, "media.book.create", "Your are allowed to create a new book"),
    BOOK_READ(2, "media.book.read", "You are allowed to get a book."),
    BOOK_UPDATE(3, "media.book.update", "Your are allowed to update a book."),
    BOOK_DELETE(4, "media.book.delete", "You are allowed to delete a book."),
    DISC_CREATE(5, "media.disc.create", "Your are allowed to create a new disc"),
    DISC_READ(6, "media.disc.read", "You are allowed to get a disc."),
    DISC_UPDATE(7, "media.disc.update", "Your are allowed to update a disc."),
    DISC_DELETE(8, "media.disc.delete", "You are allowed to delete a disc.");

    private final int id;
    private final String name;
    private final String description;

    Authorisation(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
