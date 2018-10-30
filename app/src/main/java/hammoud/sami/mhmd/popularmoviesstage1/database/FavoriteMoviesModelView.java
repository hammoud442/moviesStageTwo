package hammoud.sami.mhmd.popularmoviesstage1.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class FavoriteMoviesModelView extends AndroidViewModel {
    private LiveData<List<FavoritMoviesEntry>> tasks;
    public FavoriteMoviesModelView(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<FavoritMoviesEntry>> getTasks() {
        MoviesDatabase moviesDatabase = MoviesDatabase.getsInstance(this.getApplication());

        tasks = moviesDatabase.favoriteMovieDao().loadAllMovies();
        return tasks;
    }
}
