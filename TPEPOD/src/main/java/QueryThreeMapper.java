import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

/**
 * Created by riveign on 11/11/15.
 */
public class QueryThreeMapper implements Mapper<String, Movie, QueryObjectThree, Integer> {
    public void map(String s, Movie movie, Context<QueryObjectThree, Integer> context) {
        for (String actor : movie.getActors()) {
            for (String b : movie.getActors()) {
                if (!b.equals(actor)) {
                    context.emit(new QueryObjectThree(actor,b), 1);
                }
            }
        }
    }
}
