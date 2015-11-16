import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

/**
 * Created by riveign on 11/11/15.
 */
public class QueryTwoReducer implements ReducerFactory<Integer, QueryObjectTwo, QueryObjectTwo> {

    public Reducer<QueryObjectTwo, QueryObjectTwo> newReducer(Integer integer) {
        return new Reducer<QueryObjectTwo, QueryObjectTwo>() {
            private long top;
            private QueryObjectTwo best;

            public void beginReduce() {
                top = -1;
            }

            public void reduce(QueryObjectTwo actual) {
                if (actual.getScore() > top) {
                    best = actual;
                }
            }

            public QueryObjectTwo finalizeReduce() {
                return best;
            }
        };
    }
}
