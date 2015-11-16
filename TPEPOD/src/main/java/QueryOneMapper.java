
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import java.util.StringTokenizer;

/**
 * Created by riveign on 11/10/15.
 */
public class QueryOneMapper implements Mapper<String, Movie, String, Long> {

    public void map(String s, Movie movie, Context<String, Long> context) {

        for (String actor : movie.getActors())    {
            context.emit(actor, movie.getImdbVotes());
        }
    }
}
