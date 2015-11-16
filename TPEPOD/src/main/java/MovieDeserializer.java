import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by riveign on 11/9/15.
 */

public class MovieDeserializer extends JsonDeserializer<Movie> {
    @Override
    public Movie deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (!node.get("Type").asText().equals("movie")){
            return new Movie("",0,0.0,"",new ArrayList<String>(), 0, 0);
        }
        String name = node.get("Title").asText();
        String director = node.get("Director").asText();
        String actors = node.get("Actors").asText();
        String[] actors_array = actors.split(", ");
        List<String> actors_list = Arrays.asList(actors_array);
        Integer year = node.get("Year").asInt();
        double rating = node.get("imdbRating").asDouble();
        long meta = node.get("Metascore").asLong();
        String imdbVotesStr = node.get("imdbVotes").asText();
        if(imdbVotesStr.equals("N/A")) {
            imdbVotesStr = "0";
        }
        long imdbVotes = Long.valueOf(imdbVotesStr.replaceAll(",", ""));
        return new Movie(name,year,rating,director,actors_list, imdbVotes, meta);
    }
}
