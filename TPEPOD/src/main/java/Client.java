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

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ExecutionException;


/**
 * Created by riveign on 11/9/15.
 */
public class Client {

    private static final String MAP_NAME = "peliculas";

    public static void main(String[] args) throws IOException {

        String name = "";
        String pass = "";
        String path = "imdb-20K.json";
        int query = -1;
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            pass = prop.getProperty("pass");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }

        name = System.getProperty("name");
        path = System.getProperty("path");

        timestampMsg("Starting to read file.");
        Movie[] mArray = new ObjectMapper().readValue(new FileReader(path), Movie[].class);
        int end = mArray.length;
        Set<Movie> set = new HashSet<Movie>();

        for(int i = 0; i < end; i++){
            set.add(mArray[i]);
        }

        end = set.size();

        Movie[] movieArray = (Movie[])set.toArray(new Movie[end]);
        timestampMsg("Finished reading file.");

        System.out.println(String.format("Connecting with cluster dev-name [%s]", name));

        ClientConfig ccfg = new ClientConfig();
        ccfg.getGroupConfig().setName(name).setPassword(pass);

        String addresses = System.getProperty("addresses");

        if (addresses != null) {
            String[] arrayAddresses = addresses.split("[,;]");
            ClientNetworkConfig net = new ClientNetworkConfig();
            net.addAddress(arrayAddresses);
            ccfg.setNetworkConfig(net);
        }
        HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        IMap<String, Movie> map = client.getMap(MAP_NAME);


        for (Movie movie: movieArray){
            if (movie.getYear() != 0){
                map.put(movie.getTitle(),movie);
            }
        }

        JobTracker tracker = client.getJobTracker("default");

        KeyValueSource<String, Movie> source = KeyValueSource.fromMap(map);
        Job<String, Movie> job = tracker.newJob(source);

        String queryString = System.getProperty("query");
        try {
            query = Integer.parseInt(queryString);
        } catch(NumberFormatException e) {
            e.printStackTrace();
            return;
        }
        timestampMsg("Starting Map Reduce.");
        queryBuilder(query, job);
        System.exit(0);

    }

    public static void timestampMsg(String msg) {
        java.util.Date date = new java.util.Date();
        System.out.println(new Timestamp(date.getTime()) + ": " + msg);
    }

    public static QueryBuilder queryBuilder(int query, Job<String, Movie> job) {
        QueryBuilder queryBuilder = null;
        switch(query) {
            case 1: queryBuilder = new QueryOneBuilder();
                    String n = System.getProperty("N");
                    if(n != null) {
                        try {
                            ((QueryOneBuilder)queryBuilder).setN(Integer.parseInt(n));
                        } catch(NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            case 2: queryBuilder = new QueryTwoBuilder();
                    String top = System.getProperty("Top");
                    if(top != null) {
                        try {
                            ((QueryTwoBuilder)queryBuilder).setTop(Integer.parseInt(top));
                        } catch(NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            case 3: queryBuilder = new QueryThreeBuilder();
                    break;
            case 4: queryBuilder = new QueryFourBuilder();
                    break;
            default: throw new IllegalArgumentException();
        }
        queryBuilder.build(job);
        return queryBuilder;
    }
}
