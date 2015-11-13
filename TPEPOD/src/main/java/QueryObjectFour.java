import java.io.Serializable;

/**
 * Created by riveign on 11/11/15.
 */
public class QueryObjectFour implements Serializable {

    private String director;
    private String actor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueryObjectFour that = (QueryObjectFour) o;

        if (director != null ? !director.equals(that.director) : that.director != null) return false;
        return !(actor != null ? !actor.equals(that.actor) : that.actor != null);

    }

    @Override
    public int hashCode() {
        int result = director != null ? director.hashCode() : 0;
        result = 31 * result + (actor != null ? actor.hashCode() : 0);
        return result;
    }

    public String getDirector() {

        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public QueryObjectFour(String director, String actor) {

        this.director = director;
        this.actor = actor;
    }
}
