package edu.hm.huberneumeier.fachklassen.medien;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017-04-12
 */
public class Disc extends Medium {

    private String barcode;
    private String director;
    private int fsk;

    private Disc() {
    }

    public Disc(String barcode, String director, int fsk, String title) {
        super(title);
        this.barcode = barcode;
        this.director = director;
        this.fsk = fsk;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getDirector() {
        return director;
    }

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
}
