import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.mapreduce.Job;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by naki on 16/11/15.
 */
public class QueryTwoBuilder implements QueryBuilder<Integer, QueryObjectTwo> {
    private Map<Integer, QueryObjectTwo> ans;
    private int min;

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
    public void setTop(int top) {
        this.min = top;
    }
    public void show() {
        TreeMap<Integer, QueryObjectTwo> tree = new TreeMap<Integer, QueryObjectTwo>();
        tree.putAll(ans);
        QueryObjectTwo q;
        for(int s: tree.keySet()) {
            if(s > min || min == -1) {
                q = tree.get(s);
                System.out.println("Year: " + s + ", " + q.getName() + ", Score: " + q.getScore());
            }
        }
        Client.timestampMsg("Done.");
    }
}
