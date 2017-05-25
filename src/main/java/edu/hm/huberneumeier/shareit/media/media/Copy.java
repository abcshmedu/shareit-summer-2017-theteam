package edu.hm.huberneumeier.shareit.media.media;

/**
 * The copy definition.
 *
 * @author Tobias Huber
 * @author Andreas Neumeier
 * @version 2017 -04-12
 */
public class Copy {
    private Medium medium;
    private String owner;

    /**
     * Constructor.
     *
     * @param owner  the owner
     * @param medium the medium
     */
    public Copy(String owner, Medium medium) {
        this.medium = medium;
        this.owner = owner;
    }

    /**
     * Gets owner.
     *
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Gets medium.
     *
     * @return the medium
     */
    public Medium getMedium() {
        return medium;
    }

    @Override
    public int hashCode() {
        int result = medium != null ? medium.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
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

        Copy copy = (Copy) o;

        if (getMedium() != null ? !getMedium().equals(copy.getMedium()) : copy.getMedium() != null) {
            return false;
        }
        return getOwner() != null ? getOwner().equals(copy.getOwner()) : copy.getOwner() == null;
    }
}
