import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.mapreduce.Job;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by naki on 16/11/15.
 */
public class QueryFourBuilder implements QueryBuilder<QueryObjectFour, Integer> {
    private Map<QueryObjectFour, Integer> ans;

    public void build(Job<String, Movie> job) {
        ICompletableFuture<Map<QueryObjectFour, Integer>> future = job
                .mapper(new QueryFourMapper())
                .reducer(new QueryFourReducer())
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

