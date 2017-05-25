package edu.hm.huberneumeier.shareit.media.media;

/**
 * The Medium definition.
 *
 * @author Tobias Huber
 * @author Andreas Neumeier
 * @version 2017-04-12
 */
public class Medium {
    private String title;

    /**
     * Default constructor.
     */
    public Medium() {
    }

    /**
     * Constructor.
     *
     * @param title the title of the medium.
     */
    public Medium(String title) {
        this.title = title;
    }

    /**
     * Getter of the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Medium medium = (Medium) o;

        return title != null ? title.equals(medium.title) : medium.title == null;
    }
}
