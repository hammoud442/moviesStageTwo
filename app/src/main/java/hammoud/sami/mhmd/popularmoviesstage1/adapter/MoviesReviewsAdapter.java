package hammoud.sami.mhmd.popularmoviesstage1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hammoud.sami.mhmd.popularmoviesstage1.R;
import hammoud.sami.mhmd.popularmoviesstage1.model.MoviesReviewsResult;

public class MoviesReviewsAdapter extends RecyclerView.Adapter<MoviesReviewsAdapter.ReviewHolder> {
    private Context mContext;
    private List<MoviesReviewsResult> moviesTrailersList;


    public MoviesReviewsAdapter(Context mContext) {
        this.mContext = mContext;
        this.moviesTrailersList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item_review;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        MoviesReviewsResult reviewsResult = moviesTrailersList.get(position);
        holder.bindData(reviewsResult);

    }

    @Override
    public int getItemCount() {
        if (moviesTrailersList == null)
            return 0;
        else return moviesTrailersList.size();

    }

    public void add (List<MoviesReviewsResult> reviewsResults){

        if (reviewsResults == null || reviewsResults.size() <= 0)
            return;
        if (this.moviesTrailersList == null)
            this.moviesTrailersList = new ArrayList<>();

        this.moviesTrailersList.addAll(reviewsResults);
        notifyDataSetChanged();

    }

    public class ReviewHolder extends RecyclerView.ViewHolder{
        TextView mAuthor, mReviewDetails ;

        public ReviewHolder(View itemView) {
            super(itemView);
            mAuthor = itemView.findViewById(R.id.author);
            mReviewDetails = itemView.findViewById(R.id.review_details);
        }

        private void bindData(MoviesReviewsResult reviewsResult){
            mAuthor.setText(reviewsResult.getAuthor());
            mReviewDetails.setText(reviewsResult.getContent());
        }
    }
}
