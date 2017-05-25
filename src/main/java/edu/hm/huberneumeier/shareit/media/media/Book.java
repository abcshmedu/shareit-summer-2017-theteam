package edu.hm.huberneumeier.shareit.media.media;

/**
 * The book definition.
 *
 * @author Tobias Huber
 * @version 2017 -04-12
 */
public class Book extends Medium {
    private String author;
    private String isbn;

    /**
     * Default constructor.
     */
    private Book() {
    }

    /**
     * Constructor.
     *
     * @param title  the title
     * @param author the author
     * @param isbn   the isbn
     */
    public Book(String title, String author, String isbn) {
        super(title);
        this.author = author;
        this.isbn = isbn;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Gets isbn.
     *
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Book book = (Book) o;

        if (author != null ? !author.equals(book.author) : book.author != null) {
            return false;
        }
        return isbn != null ? isbn.equals(book.isbn) : book.isbn == null;
    }

    /**
     * Clear isbn (remove '-' and ' ').
     */
    public void clearISBN() {
        this.isbn = getIsbn().replace("-", "").replace(" ", "");
    }
}
