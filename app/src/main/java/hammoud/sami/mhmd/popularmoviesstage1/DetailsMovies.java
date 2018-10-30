package hammoud.sami.mhmd.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import hammoud.sami.mhmd.popularmoviesstage1.adapter.MoviesReviewsAdapter;
import hammoud.sami.mhmd.popularmoviesstage1.adapter.MoviesTrailersAdapter;
import hammoud.sami.mhmd.popularmoviesstage1.database.FavoritMoviesEntry;
import hammoud.sami.mhmd.popularmoviesstage1.database.MoviesDatabase;
import hammoud.sami.mhmd.popularmoviesstage1.model.Movies;
import hammoud.sami.mhmd.popularmoviesstage1.model.MoviesReviews;
import hammoud.sami.mhmd.popularmoviesstage1.model.MoviesTrailer;
import hammoud.sami.mhmd.popularmoviesstage1.model.MoviesVideos;
import hammoud.sami.mhmd.popularmoviesstage1.network.ApiRequests;
import hammoud.sami.mhmd.popularmoviesstage1.network.Client;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static hammoud.sami.mhmd.popularmoviesstage1.util.youtube.youtubeWatch;

public class DetailsMovies extends AppCompatActivity {
    private Movies movies;
    private TextView mTitleMovies,mDateMovies,mRateMovies,mDescriptionMovies;
    private ImageView mImageMovies;
    private Context context;
    public final static String BASE_POSTER_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private static final String KEY= "movies";
    ApiRequests apiRequests = Client.client().create(ApiRequests.class);
    private RecyclerView mMoviesTrailerRecycleView,mMoviesReviewRecycleReview;
    private MoviesTrailersAdapter moviesTrailersAdapter;
    private MoviesReviewsAdapter moviesReviewsAdapter;
    private FloatingActionButton floatingActionButton;
    private MoviesDatabase moviesDatabase;
    private boolean isFavourite = false;
    private int moviesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movies);
        mTitleMovies = findViewById(R.id.title);
        mDateMovies = findViewById(R.id.date);
        mMoviesTrailerRecycleView = findViewById(R.id.recycle_movies_trailer);
        mMoviesReviewRecycleReview = findViewById(R.id.recycle_movies_review);
        floatingActionButton = findViewById(R.id.add_favorite);

        mRateMovies = findViewById(R.id.rate);
        mDescriptionMovies = findViewById(R.id.description);
        mImageMovies = findViewById(R.id.image_movies);
        context = this;
        moviesDatabase = MoviesDatabase.getsInstance(context);

        Intent intent = getIntent();
        if (intent != null){
             movies = (Movies)getIntent().getSerializableExtra(KEY);
            Picasso.with(context).load(BASE_POSTER_IMAGE_URL+movies.getPosterPath()).into(mImageMovies);
            mTitleMovies.setText(movies.getTitle());
            mDateMovies.setText(movies.getReleaseDate());
            mRateMovies.setText(String.valueOf(movies.getVoteAverage()));
            mDescriptionMovies.setText(movies.getOverview());
        }

        moviesTrailersAdapter = new MoviesTrailersAdapter(this,
                trailer -> youtubeWatch(DetailsMovies.this, trailer.getKey()));
        mMoviesTrailerRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mMoviesTrailerRecycleView.setAdapter(moviesTrailersAdapter);
        mMoviesTrailerRecycleView.setNestedScrollingEnabled(false);
        moviesId = movies.getId();
        getMoviesVedioTrailer();

        moviesReviewsAdapter = new MoviesReviewsAdapter(this);
        mMoviesReviewRecycleReview.setLayoutManager(new LinearLayoutManager(this));
        mMoviesReviewRecycleReview.setAdapter(moviesReviewsAdapter);
        mMoviesReviewRecycleReview.setNestedScrollingEnabled(false);
        getMoviesReview();



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavourite){
                    moviesDatabase.favoriteMovieDao().deleteMovie(moviesDatabase.favoriteMovieDao().loadMovieById(moviesId));
                    floatingActionButton.setImageResource(R.drawable.ic_favorite);
                    isFavourite = false;
                }else {
                    moviesDatabase.favoriteMovieDao().insertMovie(new FavoritMoviesEntry(moviesId,movies.getOriginalTitle(),movies.getPosterPath(),movies.getOverview(),movies.getReleaseDate(),movies.getVoteAverage()));
                    floatingActionButton.setImageResource(R.drawable.ic_rubbish_bin);
                    isFavourite = true;
                }
            }
        });

        settingAdd();
    }

    private void getMoviesVedioTrailer(){
        apiRequests.getMoviesVideos(moviesId+"").enqueue(new Callback<MoviesVideos>() {
            @Override
            public void onResponse(Call<MoviesVideos> call, Response<MoviesVideos> response) {
                MoviesVideos moviesVideos = response.body();
                moviesTrailersAdapter.add(moviesVideos.getTrailers());
            }
            @Override
            public void onFailure(Call<MoviesVideos> call, Throwable t) {
            }
        });

    }

    private void getMoviesReview(){
        apiRequests.getMoviesReviews(moviesId+"").enqueue(new Callback<MoviesReviews>() {
            @Override
            public void onResponse(Call<MoviesReviews> call, Response<MoviesReviews> response) {
                MoviesReviews moviesReviews = response.body();
                moviesReviewsAdapter.add(moviesReviews.getReviewList());
            }

            @Override
            public void onFailure(Call<MoviesReviews> call, Throwable t) {

            }
        });
    }

    private void settingAdd(){
        if (moviesDatabase.favoriteMovieDao().loadMovieById(moviesId) != null){
            floatingActionButton.setImageResource(R.drawable.ic_rubbish_bin);
            isFavourite = true;
        }
        else {
            floatingActionButton.setImageResource(R.drawable.ic_favorite);
            isFavourite = false;
        }
    }
}
