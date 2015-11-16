import com.hazelcast.mapreduce.Job;

/**
 * Created by naki on 16/11/15.
 */
public interface QueryBuilder<K, V> {
    void build(Job<String, Movie> job);

    void show();
}
