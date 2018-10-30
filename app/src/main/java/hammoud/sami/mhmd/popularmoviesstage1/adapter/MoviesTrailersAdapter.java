package hammoud.sami.mhmd.popularmoviesstage1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hammoud.sami.mhmd.popularmoviesstage1.R;
import hammoud.sami.mhmd.popularmoviesstage1.model.MoviesTrailer;

public class MoviesTrailersAdapter extends RecyclerView.Adapter<MoviesTrailersAdapter.TrailerHolder> {
    private Context mContext;
    private List<MoviesTrailer> moviesTrailersList;
    private OnItemClickListener mOnItemClickListener;


    public MoviesTrailersAdapter(Context context, OnItemClickListener listener) {
        this.mContext = context;
        this.mOnItemClickListener= listener;
        this.moviesTrailersList = new ArrayList<>();
    }

    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item_trailer;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new TrailerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder holder, int position) {
        MoviesTrailer moviesTrailer = moviesTrailersList.get(position);
        holder.bindData(moviesTrailer);
    }

    @Override
    public int getItemCount() {
        if (moviesTrailersList == null)
            return 0;
        else
            return moviesTrailersList.size();
    }

    public void add (List<MoviesTrailer> moviesTrailers){
        if (moviesTrailers == null || moviesTrailers.size() <= 0) {
            return;
        }
        if (this.moviesTrailersList == null) {
            this.moviesTrailersList = new ArrayList<>();
        }
        this.moviesTrailersList.addAll(moviesTrailers);
        notifyDataSetChanged();
    }

    public class TrailerHolder extends RecyclerView.ViewHolder{

        ImageView mImageTrailer;
        TextView mTitleTrailer;

        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClicked(moviesTrailersList.get(getAdapterPosition()));
            }
        };

        public TrailerHolder(View itemView) {
            super(itemView);
            mImageTrailer = itemView.findViewById(R.id.play);
            mTitleTrailer = itemView.findViewById(R.id.title_trailer);
            itemView.setOnClickListener(onClickListener);
            mImageTrailer.setOnClickListener(onClickListener);
            mTitleTrailer.setOnClickListener(onClickListener);

        }

        private void bindData(MoviesTrailer moviesTrailer){
            mTitleTrailer.setText(moviesTrailer.getName());
        }

    }

    public interface OnItemClickListener {
        void onItemClicked(MoviesTrailer trailer);
    }


}
