package hammoud.sami.mhmd.popularmoviesstage1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesVideos {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<MoviesTrailer> trailers = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MoviesTrailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<MoviesTrailer> trailers) {
        this.trailers = trailers;
    }
}
