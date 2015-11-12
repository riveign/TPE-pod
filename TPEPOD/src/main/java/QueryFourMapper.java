import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

/**
 * Created by riveign on 11/11/15.
 */
public class QueryFourMapper implements Mapper<String, Movie, QueryObjectFour, Integer> {
    public void map(String s, Movie movie, Context<QueryObjectFour, Integer> context) {
        for (String actor : movie.getActors())    {
            context.emit(new QueryObjectFour(movie.Director,actor),1);
        }
    }
}
