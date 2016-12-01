package com.govibs.popularmovie.utils;

import android.util.Log;

import com.govibs.popularmovie.movie.MovieDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Movie data parser for parsing application
 * Created by Vibhor on 11/30/16.
 */

public final class MovieDataUtils {

    private static final String KEY_RESULTS = "results";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_TITLE = "title";
    private static final String KEY_POPULARITY = "popularity";
    private static final String KEY_VOTE = "vote_average";
    private static final String KEY_ID = "id";
    private static final String KEY_RELEASE_DATE = "release_date";

    /**
     * Get the Movie Details from the popular movie response
     * @param response Response from the service call
     * @return ArrayList of {@link MovieDetails} object.
     */
    public static ArrayList<MovieDetails> getMovieDetailsFromResponse(JSONObject response) {
        ArrayList<MovieDetails> movieDetailsArrayList = new ArrayList<>();
        try {
            Log.v("VIBS", response.toString());
            if (response.has(KEY_RESULTS)) {
                JSONArray results = response.getJSONArray(KEY_RESULTS);
                for (int counter = 0; counter < results.length(); counter++) {
                    MovieDetails movieDetails = new MovieDetails();
                    JSONObject movieItemObject = results.getJSONObject(counter);
                    movieDetails.setId(movieItemObject.optInt(KEY_ID, -1));
                    movieDetails.setMoveVote(movieItemObject.optDouble(KEY_VOTE, 0));
                    movieDetails.setMovieDescription(movieItemObject.optString(KEY_OVERVIEW, ""));
                    movieDetails.setMoviePosterUrl(movieItemObject.optString(KEY_POSTER_PATH, ""));
                    movieDetails.setMovieTitle(movieItemObject.optString(KEY_TITLE, ""));
                    movieDetails.setMoviePosterUrl(movieItemObject.optString(KEY_POSTER_PATH, ""));
                    movieDetails.setPopularity(movieItemObject.optDouble(KEY_POPULARITY, 0));
                    movieDetails.setReleaseDate(movieItemObject.optString(KEY_RELEASE_DATE, ""));
                    movieDetailsArrayList.add(movieDetails);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return movieDetailsArrayList;
    }

}
