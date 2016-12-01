package com.govibs.popularmovie.movie;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.govibs.popularmovie.R;

import java.util.ArrayList;

/**
 * Movie List adapter
 * Created by Vibhor on 11/30/16.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {

    private ArrayList<MovieDetails> mMovieDetailsArrayList = new ArrayList<>();

    public static class MovieListViewHolder extends RecyclerView.ViewHolder {

        private NetworkImageView moviePoster;
        private TextView movieTitle;

        public MovieListViewHolder(View view) {
            super(view);
            moviePoster = (NetworkImageView) view.findViewById(R.id.networkImageViewMoviePoster);
            movieTitle = (TextView) view.findViewById(R.id.tvMovieTitle);
        }

    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_item, parent, false);
        return new MovieListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, int position) {
        holder.movieTitle.setText(mMovieDetailsArrayList.get(position).getMovieTitle());
        
    }

    @Override
    public int getItemCount() {
        return mMovieDetailsArrayList.size();
    }
}
