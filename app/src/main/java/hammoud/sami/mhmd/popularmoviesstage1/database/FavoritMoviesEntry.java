package hammoud.sami.mhmd.popularmoviesstage1.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "moviefav")
public class FavoritMoviesEntry {

    @PrimaryKey
    private int movieId;
    private String movieName;
    private String moviePoster;
    private String movieOverview;
    private String movieReleaseDate ;
    private Double movieVoteAverage;

    public FavoritMoviesEntry(int movieId, String movieName, String moviePoster,String movieOverview,String movieReleaseDate , double movieVoteAverage) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.moviePoster = moviePoster;
        this.movieOverview = movieOverview;
        this.movieReleaseDate = movieReleaseDate;
        this.movieVoteAverage = movieVoteAverage;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public Double getMovieVoteAverage() {
        return movieVoteAverage;
    }

    public void setMovieVoteAverage(Double movieVoteAverage) {
        this.movieVoteAverage = movieVoteAverage;
    }
}
