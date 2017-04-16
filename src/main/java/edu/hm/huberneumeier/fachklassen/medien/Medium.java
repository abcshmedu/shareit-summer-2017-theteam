package edu.hm.huberneumeier.fachklassen.medien;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017-04-12
 */
public class Medium {
    private String title;

    public Medium(){

    }

    public Medium(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Medium medium = (Medium) o;

        return title != null ? title.equals(medium.title) : medium.title == null;
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Medium{" +
                "title='" + title + '\'' +
                '}';
    }
}
