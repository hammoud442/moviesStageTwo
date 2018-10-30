package hammoud.sami.mhmd.popularmoviesstage1.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {FavoritMoviesEntry.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {
    private static final String TAG = MoviesDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "MoviesFavList";
    private static MoviesDatabase sInstance;

    public static MoviesDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Creating New Database Instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MoviesDatabase.class,
                        MoviesDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(TAG, "Getting the database instance");
        return sInstance;
    }


    public abstract FavoriteMovieDao favoriteMovieDao();
}
