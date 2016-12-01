package com.govibs.popularmovie;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.govibs.popularmovie.movie.MovieDetails;
import com.govibs.popularmovie.network.NetworkManager;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String PARAM_MOVIE_DETAIL = "param_movie_detail";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        final MovieDetails movieDetails = (MovieDetails) getIntent().getSerializableExtra(PARAM_MOVIE_DETAIL);
        if (movieDetails == null) {
            finish();
        } else {
            setTitle(movieDetails.getMovieTitle());
            populateData(movieDetails);
        }
    }

    private void populateData(final MovieDetails movieDetails) {
        TextView tvMovieRating = (TextView) findViewById(R.id.tvMovieRating);
        tvMovieRating.setText(getString(R.string.text_movie_rating, movieDetails.getMoveVote()));
        TextView tvPopularity = (TextView) findViewById(R.id.tvMoviePopularity);
        tvPopularity.setText(getString(R.string.text_movie_popularity, movieDetails.getPopularity()));
        TextView tvOverview = (TextView) findViewById(R.id.tvMovieOverview);
        tvOverview.setText(movieDetails.getMovieDescription());
        NetworkImageView networkImageView = (NetworkImageView) findViewById(R.id.networkImageViewMoviePoster);
        networkImageView.setDefaultImageResId(R.drawable.popular_movies_powered_by_movie_db);
        final String url = Uri.parse(getString(R.string.base_url_image)).buildUpon().build().toString() + movieDetails.getMoviePosterUrl();
        networkImageView.setImageUrl(url, NetworkManager.getInstance(getApplicationContext()).getImageLoader());
        TextView tvMovieReleaseDate = (TextView) findViewById(R.id.tvMovieReleaseDate);
        tvMovieReleaseDate.setText(getString(R.string.text_release_date, movieDetails.getReleaseDate()));
    }
}
