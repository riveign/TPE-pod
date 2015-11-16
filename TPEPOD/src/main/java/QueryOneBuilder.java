import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.mapreduce.Job;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by naki on 16/11/15.
 */
public class QueryOneBuilder implements QueryBuilder<String, Long> {

    private Map<String, Long> ans;
    private int n;

    public void build(Job<String, Movie> job) {
        ICompletableFuture<Map<String, Long>> future = job
                .mapper(new QueryOneMapper())
                .reducer(new QueryOneReducer())
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

    public void setN(int n) {
        this.n = n;
    }

    public void show() {
        TreeMap<String, Long> tree = new TreeMap<String, Long>(new Comparator<String>() {
            public int compare(String a, String b) {
                return ans.get(b).compareTo(ans.get(a));
            }
        });
        tree.putAll(ans);
        if(n <= 0) {
            n = tree.size();
        }
        int i = 0;
        for(String s: tree.keySet()) {
            if(i < n) {
                System.out.println("Actor: " + s + ", Votos: " + tree.get(s));
                i++;
            }
        }
        Client.timestampMsg("Done.");
    }
}
