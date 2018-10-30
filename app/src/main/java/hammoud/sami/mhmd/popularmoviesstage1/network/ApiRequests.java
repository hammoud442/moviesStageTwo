package hammoud.sami.mhmd.popularmoviesstage1.network;

import hammoud.sami.mhmd.popularmoviesstage1.model.MoviesList;
import hammoud.sami.mhmd.popularmoviesstage1.model.MoviesReviews;
import hammoud.sami.mhmd.popularmoviesstage1.model.MoviesVideos;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequests {

    String API_KEY = "f8d4028291310230b4fadb8e38cdc015";

    @GET("movie/popular?api_key=" + API_KEY)
    Call<MoviesList> getPopularMovies(@Query("language") String language);

    @GET("movie/top_rated?api_key=" + API_KEY)
    Call<MoviesList> getTopRatedMovies(@Query("language") String language);

    @GET("movie/{id}/videos?api_key=" + API_KEY + "&language=en-US")
    Call<MoviesVideos> getMoviesVideos(@Path("id") String movieId);

    @GET("movie/{id}/reviews?api_key=" + API_KEY + "&language=en-US")
    Call<MoviesReviews> getMoviesReviews(@Path("id") String movieId);

}
