import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.mapreduce.Job;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by naki on 16/11/15.
 */
public class QueryTwoBuilder implements QueryBuilder<Integer, QueryObjectTwo> {
    private Map<Integer, QueryObjectTwo> ans;

    public void build(Job<String, Movie> job) {
        ICompletableFuture<Map<Integer, QueryObjectTwo>> future = job
                .mapper(new QueryTwoMapper())
                .reducer(new QueryTwoReducer())
                .submit();
        try {
            ans = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        show();
    }

    public void show() {
        System.out.println(ans);
        Client.timestampMsg("Done.");
    }
}
