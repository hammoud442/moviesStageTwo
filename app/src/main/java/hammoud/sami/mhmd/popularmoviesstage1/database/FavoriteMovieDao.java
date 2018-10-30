package hammoud.sami.mhmd.popularmoviesstage1.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM moviefav")
    LiveData<List<FavoritMoviesEntry>> loadAllMovies();

    @Insert
    void insertMovie(FavoritMoviesEntry movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(FavoritMoviesEntry movie);

    @Delete
    void deleteMovie(FavoritMoviesEntry movie);

    @Query("SELECT * FROM moviefav WHERE movieId == :id")
    FavoritMoviesEntry loadMovieById(int id);
}
