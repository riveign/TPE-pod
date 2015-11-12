import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

/**
 * Created by riveign on 11/10/15.
 */
public class QueryOneReducer implements ReducerFactory<String, Long, Long> {
    public Reducer<Long, Long> newReducer(String s) {
        return new Reducer<Long, Long>()
        {
            private long sum;

            public void beginReduce()
            {
                sum= 0;
            }

            public void reduce(Long value)
            {
                sum += value;
            }

            public Long finalizeReduce()
            {
                return sum ;
            }
        };
    }
}
