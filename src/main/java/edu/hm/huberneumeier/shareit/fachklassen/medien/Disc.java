package edu.hm.huberneumeier.shareit.fachklassen.medien;

/**
 * The disc definition.
 *
 * @author Tobias Huber, Andreas Neumeier
 * @version 2017 -04-12
 */
public class Disc extends Medium {

    private String barcode;
    private String director;
    private int fsk;

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
    public Disc(String barcode, String director, int fsk, String title) {
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
    public int getFsk() {
        return fsk;
    }

    @Override
    public String toString() {
        return "Disc{" +
                "barcode='" + barcode + '\'' +
                ", director='" + director + '\'' +
                ", fsk=" + fsk +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Disc disc = (Disc) o;

        if (fsk != disc.fsk) return false;
        if (barcode != null ? !barcode.equals(disc.barcode) : disc.barcode != null) return false;
        return director != null ? director.equals(disc.director) : disc.director == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (barcode != null ? barcode.hashCode() : 0);
        result = 31 * result + (director != null ? director.hashCode() : 0);
        result = 31 * result + fsk;
        return result;
    }

    /**
     * Clear barcode (remove '-' and ' ').
     */
    public void clearBarcode(){
        this.barcode = getBarcode().replace("-", "").replace(" ", "");
    }
}
