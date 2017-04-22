package edu.hm.huberneumeier.shareit.fachklassen.medien;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017-04-12
 */
public class Copy {
    private Medium medium;
    private String owner;

    public Copy(String owner, Medium medium) {
        this.medium = medium;
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public Medium getMedium() {
        return medium;
    }
}