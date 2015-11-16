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
public class QueryThreeBuilder implements QueryBuilder<QueryObjectThree, Integer> {
    private Map<QueryObjectThree, List<Movie>> ans;

    public void build(Job<String, Movie> job) {
        ICompletableFuture<Map<QueryObjectThree, List<Movie>>> future = job
                .mapper(new QueryThreeMapper())
                .reducer(new QueryThreeReducer())
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
        TreeMap<QueryObjectThree, List<Movie>> tree = new TreeMap<QueryObjectThree, List<Movie>>(new Comparator<QueryObjectThree>() {
            public int compare(QueryObjectThree a, QueryObjectThree b) {
                return ans.get(b).size() - ans.get(a).size();
            }
        });
        tree.putAll(ans);
        List<Movie> list;
        for(QueryObjectThree s: tree.keySet()) {
            list = tree.get(s);
            System.out.println("Actors: " + s.getActorOne() + ", " + s.getActorTwo() + ", Appearances: " + list.size());
            for(Movie m : list) {
                System.out.print(m.getTitle() + "   ");
            }
            System.out.println();
        }
        Client.timestampMsg("Done.");
    }
}

