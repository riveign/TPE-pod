import java.io.Serializable;

/**
 * Created by riveign on 11/11/15.
 */
public class QueryObjectTwo implements Serializable {

    private String name;
    private Long score;

    public QueryObjectTwo(String name, Long score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
