package hammoud.sami.mhmd.popularmoviesstage1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hammoud.sami.mhmd.popularmoviesstage1.R;
import hammoud.sami.mhmd.popularmoviesstage1.database.FavoritMoviesEntry;
import hammoud.sami.mhmd.popularmoviesstage1.model.Movies;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private List<Movies> movieList;
    public final static String BASE_POSTER_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private Context context;


    public Adapter(Context context, OnItemClickListener onItemClickListener ){
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.movieList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item_recycle_movies;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(movieList.get(position),onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mPosterImageView;
        TextView mTitleMoviesText;
        public ViewHolder(View itemView) {
            super(itemView);
            mPosterImageView = itemView.findViewById(R.id.poster_image_movies);
            mTitleMoviesText = itemView.findViewById(R.id.movies_title);
        }

        private void bind (final Movies movies, final OnItemClickListener onItemClickListener){

            String ImagePosterUrl = BASE_POSTER_IMAGE_URL+movies.getPosterPath();
            Picasso.with(context).load(ImagePosterUrl).into(mPosterImageView);
            mTitleMoviesText.setText(movies.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(movies);
                }
            });

        }


    }

    public void clear(){
        movieList.clear();
        notifyDataSetChanged();
    }

    public void add (List<Movies> movies){
        if (movies != null){
            for (Movies movie : movies){
                movieList.add(movie);
            }
        }

    }

    public void addFavorite(Movies movie) {
        movieList.add(movie);
        notifyItemInserted(movieList.size() - 1);
    }

    public void addFavorite(List<FavoritMoviesEntry> favoritMoviesEntries) {
        for (FavoritMoviesEntry favoritMoviesEntry : favoritMoviesEntries) {
            addFavorite(new Movies(favoritMoviesEntry.getMovieId(), favoritMoviesEntry.getMovieName(), favoritMoviesEntry.getMoviePoster(),favoritMoviesEntry.getMovieOverview(),favoritMoviesEntry.getMovieReleaseDate(),favoritMoviesEntry.getMovieVoteAverage()));
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Movies movie);
    }


}
