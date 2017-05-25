package edu.hm.huberneumeier.shareit.media.media;

/**
 * The disc definition.
 *
 * @author Tobias Huber
 * @author Andreas Neumeier
 * @version 2017 -04-12
 */
public class Disc extends Medium {

    private String barcode;
    private String director;
    private Integer fsk;

    /**
     * Default constructor.
     */
    private Disc() {
    }

    /**
     * Constructor.
     *
     * @param barcode  the barcode of a disc.
     * @param director the director of a disc.
     * @param fsk      the fsk of a disc.
     * @param title    the title of a disc.
     */
    public Disc(String barcode, String director, Integer fsk, String title) {
        super(title);
        this.barcode = barcode;
        this.director = director;
        this.fsk = fsk;
    }

    /**
     * Gets barcode.
     *
     * @return the barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Gets director.
     *
     * @return the director
     */
    public String getDirector() {
        return director;
    }

    /**
     * Gets fsk.
     *
     * @return the fsk
     */
    public Integer getFsk() {
        return fsk;
    }

    @Override
    public int hashCode() {
        int result = barcode != null ? barcode.hashCode() : 0;
        result = 31 * result + (director != null ? director.hashCode() : 0);
        result = 31 * result + (fsk != null ? fsk.hashCode() : 0);
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

        Disc disc = (Disc) o;

        if (fsk != disc.fsk) {
            return false;
        }
        if (barcode != null ? !barcode.equals(disc.barcode) : disc.barcode != null) {
            return false;
        }
        return director != null ? director.equals(disc.director) : disc.director == null;
    }

    /**
     * Clear barcode (remove '-' and ' ').
     */
    public void clearBarcode() {
        this.barcode = getBarcode().replace("-", "").replace(" ", "");
    }
}
