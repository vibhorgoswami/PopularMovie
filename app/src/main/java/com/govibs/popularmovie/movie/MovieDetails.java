package com.govibs.popularmovie.movie;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Movie details object
 * Created by Vibhor on 11/30/16.
 */

public final class MovieDetails implements Serializable {

    private String movieTitle;
    private Bitmap moviePoster;

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public Bitmap getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(Bitmap moviePoster) {
        this.moviePoster = moviePoster;
    }
}
