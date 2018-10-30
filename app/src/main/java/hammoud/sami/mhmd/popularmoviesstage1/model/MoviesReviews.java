package hammoud.sami.mhmd.popularmoviesstage1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesReviews {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<MoviesReviewsResult> reviewList = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MoviesReviewsResult> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<MoviesReviewsResult> reviewList) {
        this.reviewList = reviewList;
    }

}
