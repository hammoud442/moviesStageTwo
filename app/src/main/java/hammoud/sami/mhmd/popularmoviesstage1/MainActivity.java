package hammoud.sami.mhmd.popularmoviesstage1;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import hammoud.sami.mhmd.popularmoviesstage1.adapter.Adapter;
import hammoud.sami.mhmd.popularmoviesstage1.database.FavoritMoviesEntry;
import hammoud.sami.mhmd.popularmoviesstage1.database.FavoriteMoviesModelView;
import hammoud.sami.mhmd.popularmoviesstage1.database.MoviesDatabase;
import hammoud.sami.mhmd.popularmoviesstage1.model.Movies;
import hammoud.sami.mhmd.popularmoviesstage1.model.MoviesList;
import hammoud.sami.mhmd.popularmoviesstage1.network.ApiRequests;
import hammoud.sami.mhmd.popularmoviesstage1.network.Client;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiRequests apiRequests = Client.client().create(ApiRequests.class);
    private static final String KEY = "movies";
    private static final String STATE = "state";
    private RecyclerView mRecyclerView;
    private ImageView mInternetImageView;
    public Adapter mAdapter;
    private Context mContext;
    private int state;
    private GridLayoutManager gridLayoutManager;
    private static final String TAG = "mainactivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycle_review_movies);
        mInternetImageView = findViewById(R.id.no_intenet_connection_image);
        mContext = this;
        Log.d(TAG, "onCreate: ");

        mAdapter = new Adapter(this, new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movies movies) {
                Intent intent = new Intent(MainActivity.this, DetailsMovies.class);
                intent.putExtra(KEY, movies);
                startActivity(intent);
            }
        });

        gridLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        network(mContext);

        if (savedInstanceState == null) {
            getPopualr();
            state = R.id.popular_movies;
        } else {

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.popular_movies:
                network(mContext);
                getPopualr();
                state = R.id.popular_movies;
                break;
            case R.id.top_reated_movies:
                network(mContext);
                topRated();
                state = R.id.top_reated_movies;
                break;
            case R.id.favoriteMovies:
                mInternetImageView.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                state = R.id.favoriteMovies;
                getFavorite();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getPopualr() {
        apiRequests.getPopularMovies(null).enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                MoviesList popularMoveList = response.body();
                mAdapter.clear();
                mAdapter.add(popularMoveList.getMovieList());
            }

            @Override
            public void onFailure(Call<MoviesList> call, Throwable t) {
            }
        });
    }


    private void topRated() {
        apiRequests.getTopRatedMovies(null).enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                MoviesList topMoviesRated = response.body();
                mAdapter.clear();
                mAdapter.add(topMoviesRated.getMovieList());
            }

            @Override
            public void onFailure(Call<MoviesList> call, Throwable t) {
            }
        });
    }

    private void getFavorite() {
        FavoriteMoviesModelView viewModel = ViewModelProviders.of(this).get(FavoriteMoviesModelView.class);
        viewModel.getTasks().observe(this, new Observer<List<FavoritMoviesEntry>>() {
            @Override
            public void onChanged(@Nullable List<FavoritMoviesEntry> favoritMoviesEntries) {
                mAdapter.clear();
                mAdapter.addFavorite(favoritMoviesEntries);
            }
        });
    }

    private void network(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (networkInfo == null) {
            mInternetImageView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
        } else {
            mInternetImageView.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE, state);
        Log.d(TAG, "onSaveInstanceState: ");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(TAG, "onRestoreInstanceState: ");

        state = savedInstanceState.getInt(STATE);
        switch (state) {
            case R.id.popular_movies:
                network(mContext);
                getPopualr();
                state = R.id.popular_movies;
                break;
            case R.id.top_reated_movies:
                network(mContext);
                topRated();
                state = R.id.top_reated_movies;
                break;
            case R.id.favoriteMovies:
                mInternetImageView.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                state = R.id.favoriteMovies;
                getFavorite();
                break;
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(TAG, "onPostResume: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
