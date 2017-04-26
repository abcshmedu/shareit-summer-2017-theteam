package edu.hm.huberneumeier.shareit.fachklassen.medien;

/**
 * The copy definition.
 *
 * @author Tobias Huber
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
}
