import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by riveign on 11/9/15.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonDeserialize(using=MovieDeserializer.class)
public class Movie implements DataSerializable {

    @JsonProperty("Title")
    protected String Title;
    @JsonProperty("Director")
    protected String Director;
    @JsonProperty("Year")
    private Integer Year;
    @JsonProperty("imdbVotes")
    private long imdbVotes;
    @JsonProperty("Metascore")
    private long Metascore;

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public long getMetascore() {
        return Metascore;
    }

    public void setMetascore(long metascore) {
        Metascore = metascore;
    }

    public long getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(long imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    @JsonProperty("imdbRating")
    private Double imdbRating;

    @Override
    public String toString() {
        return "Movie{" +
                "Title='" + Title + '\'' +
                ", Director='" + Director + '\'' +
                ", Year=" + Year +
                ", imdbRating=" + imdbRating +
                ", Actors=" + Actors +
                '}';
    }

    @JsonProperty("Actors")
    private List<String> Actors;

    public Movie() {
    }

    public Movie(String title, Integer year, Double imdbRating, String director, List<String> Actors, long imdbVotes, long metascore) {
        this.imdbVotes = imdbVotes;
        Metascore = metascore;
        setImdbRating(imdbRating);
        setActors(Actors);
        Title = title;
        Year = year;
        Director = director;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer year) {
        Year = year;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public List<String> getActors() {
        return Actors;
    }

    public void setActors(List<String> actors) {
        Actors = actors;
    }

    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(Actors.size());
        for(String actor : Actors){
            out.writeUTF(actor);
        }
        out.writeLong(imdbVotes);
        out.writeLong(Metascore);
        out.writeUTF(Title);
        out.writeInt(Year);
        out.writeUTF(Director);
    }

    public void readData(ObjectDataInput in) throws IOException {
        int actorsLength = in.readInt();
        Actors = new ArrayList<String>();
        for(int i = 0; i < actorsLength; i++) {
            Actors.add(in.readUTF());
        }
        imdbVotes = in.readLong();
        Metascore = in.readLong();
        Title = in.readUTF();
        Year = in.readInt();
        Director = in.readUTF();
    }
}
