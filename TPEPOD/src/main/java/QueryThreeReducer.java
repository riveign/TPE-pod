import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

/**
 * Created by riveign on 11/11/15.
 */
public class QueryThreeReducer implements ReducerFactory<QueryObjectThree, Integer, Integer> {
    public Reducer<Integer, Integer> newReducer(QueryObjectThree queryObjectThree) {
        return new Reducer<Integer, Integer>()
        {
            private int sum;

            public void beginReduce()
            {
                sum= 0;
            }

            public void reduce(Integer value)
            {
                sum += value;
            }

            public Integer finalizeReduce()
            {
                return sum ;
            }
        };
    }
}
