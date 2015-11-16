import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by riveign on 11/11/15.
 */
public class QueryThreeReducer implements ReducerFactory<QueryObjectThree, Movie, List<Movie>> {
    public Reducer<Movie, List<Movie>> newReducer(QueryObjectThree queryObjectThree) {
        return new Reducer<Movie, List<Movie>>()
        {
            private List<Movie> list;

            public void beginReduce()
            {
                list = new LinkedList<Movie>();
            }

            public void reduce(Movie value)
            {
                if(!list.contains(value)) {
                    list.add(value);
                };
            }

            public List<Movie> finalizeReduce()
            {
                return list;
            }
        };
    }
}
