import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

/**
 * Created by riveign on 11/11/15.
 */
public class QueryTwoMapper implements Mapper<String, Movie, Integer, QueryObjectTwo> {

    public void map(String s, Movie movie, Context<Integer, QueryObjectTwo> context) {
        QueryObjectTwo qo = new QueryObjectTwo(movie.getTitle(),movie.getMetascore());
        context.emit(movie.getYear(),qo);
    }
}
