package edu.hm.huberneumeier.shareit.auth.media;

import java.util.HashMap;
import java.util.Map;

/**
 * Containing all authorizations available. For each media operation one.
 *
 * @author Tobias Huber
 * @version 28.05.2017
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
    private static Map<Integer, Authorisation> map = new HashMap<Integer, Authorisation>();

    static {
        for (Authorisation elem : Authorisation.values()) {
            map.put(elem.getId(), elem);
        }
    }

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

    public static Authorisation valueOf(int id) {
        return map.get(id);
    }
}
