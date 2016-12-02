package com.govibs.popularmovie.movie;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.govibs.popularmovie.R;
import com.govibs.popularmovie.network.NetworkManager;

import java.util.ArrayList;

/**
 * Movie List adapter
 * Created by Vibhor on 11/30/16.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {

    private ArrayList<MovieDetails> mMovieDetailsArrayList = new ArrayList<>();
    private Context mContext;
    private IMovieDetailCallback mIMovieDetailCallback;

    public static class MovieListViewHolder extends RecyclerView.ViewHolder {

        private NetworkImageView moviePoster;
        private TextView movieTitle;
        private CardView mCardView;

        public MovieListViewHolder(View view) {
            super(view);
            mCardView = (CardView) view.findViewById(R.id.cardviewMovieItem);
            moviePoster = (NetworkImageView) view.findViewById(R.id.networkImageViewMoviePoster);
            movieTitle = (TextView) view.findViewById(R.id.tvMovieTitle);
        }

    }

    /***
     * Moview list adapter
     * @param context the calling application context
     * @param movieDetailsArrayList the movie details array list
     */
    public MovieListAdapter(Context context, ArrayList<MovieDetails> movieDetailsArrayList, IMovieDetailCallback movieDetailCallback) {
        mContext = context;
        mMovieDetailsArrayList = movieDetailsArrayList;
        mIMovieDetailCallback = movieDetailCallback;
    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_item, parent, false);
        return new MovieListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieListViewHolder holder, int position) {
        holder.movieTitle.setText(mMovieDetailsArrayList.get(position).getMovieTitle());
        holder.moviePoster.setDefaultImageResId(R.drawable.popular_movies_powered_by_movie_db);
        final String url = Uri.parse(mContext.getString(R.string.base_url_image)).buildUpon().build().toString()
                + mMovieDetailsArrayList.get(position).getMoviePosterUrl();
        holder.moviePoster.setImageUrl(url, NetworkManager.getInstance(mContext.getApplicationContext()).getImageLoader());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIMovieDetailCallback != null) {
                    mIMovieDetailCallback.onMovieSelected(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieDetailsArrayList.size();
    }

    /**
     * Swap the cursor data
     * @param movieDetailsArrayList the movie details array list
     */
    public void swapData(ArrayList<MovieDetails> movieDetailsArrayList) {
        mMovieDetailsArrayList = movieDetailsArrayList;
        notifyDataSetChanged();
    }
}
