import java.io.Serializable;

/**
 * Created by riveign on 11/11/15.
 */
public class QueryObjectThree implements Serializable {

    private String actorOne;
    private String actorTwo;

    public QueryObjectThree(String actorOne, String actorTwo) {
        this.actorOne = actorOne;
        this.actorTwo = actorTwo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueryObjectThree that = (QueryObjectThree) o;

        return (actorOne.equals(that.actorOne) && actorTwo.equals(that.actorTwo)) || (actorTwo.equals(that.actorOne) && actorOne.equals(that.actorTwo));
    }

    @Override
    public int hashCode() {
        int result = actorOne != null ? actorOne.hashCode() : 0;
        result = 31 * result + (actorTwo != null ? actorTwo.hashCode() : 0);
        return result;
    }

    public String getActorTwo() {

        return actorTwo;
    }

    public void setActorTwo(String actorTwo) {
        this.actorTwo = actorTwo;
    }

    public String getActorOne() {

        return actorOne;
    }

    public void setActorOne(String actorOne) {
        this.actorOne = actorOne;
    }
}
