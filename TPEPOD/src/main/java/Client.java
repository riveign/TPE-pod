import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.module.SimpleModule;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by riveign on 11/9/15.
 */
public class Client {

    private static final String MAP_NAME = "refranes";

    public static void main(String[] args) throws IOException {
        Movie[] movieArray = new ObjectMapper().readValue(new FileReader("imdb-20K.json"), Movie[].class);

        // TODO: Where do I get these properties from?
        String name = System.getProperty("name");
        if(name == null){
            name = "dev";
        }
        String pass = System.getProperty("pass");
        if (pass == null) {
            pass = "dev-pass";
        }
        System.out.println(String.format("Connecting with cluster dev-name [%s]", name));

        ClientConfig ccfg = new ClientConfig();
        ccfg.getGroupConfig().setName(name).setPassword(pass);

        String addresses = System.getProperty("addresses");
        System.out.println(addresses);
        if (addresses != null) {
            String[] arrayAddresses = addresses.split("[,;]");
            ClientNetworkConfig net = new ClientNetworkConfig();
            net.addAddress(arrayAddresses);
            ccfg.setNetworkConfig(net);
        }
        HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        System.out.println(client.getCluster());

        IMap<String, Movie> map = client.getMap(MAP_NAME);


        for (Movie movie: movieArray){
            if (movie.getYear() != 0){
                map.put(movie.getTitle(),movie);
            }
        }

        JobTracker tracker = client.getJobTracker("default");

        KeyValueSource<String, Movie> source = KeyValueSource.fromMap(map);

        Job<String, Movie> job = tracker.newJob(source);

        ICompletableFuture<Map<String, Long>> future = job
                .mapper(new QueryOneMapper())
                .reducer(new QueryOneReducer())
                .submit();

        Map<String, Long> a = null;
        try {
            a = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(a);

        System.exit(0);



    }
}
