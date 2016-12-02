package com.govibs.popularmovie.movie;

/**
 * Callback listener
 * Created by Vibhor on 12/1/16.
 */

public interface IMovieDetailCallback {

    /**
     * Movie Selected callback
     * @param movieDetail the movie item that was selected.
     */
    void onMovieSelected(MovieDetails movieDetail);

}
