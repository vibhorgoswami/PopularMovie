package com.govibs.popularmovie;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.govibs.popularmovie.movie.MovieDetails;
import com.govibs.popularmovie.movie.MovieListAdapter;
import com.govibs.popularmovie.network.INetworkResponse;
import com.govibs.popularmovie.network.NetworkManager;
import com.govibs.popularmovie.network.NetworkResponse;
import com.govibs.popularmovie.storage.PopularMoviePreferences;
import com.govibs.popularmovie.utils.MovieDataUtils;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements INetworkResponse{


    private ArrayList<MovieDetails> mMovieDetailsArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MovieListAdapter mMovieListAdapter;
    private ProgressBar progressBarLoader;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getMediaDetailFromServer();
    }

    @Override
    public void onNetworkResponse(NetworkResponse networkResponse) {
        if (networkResponse.getVolleyError() == null) {
            loadMediaDetail(networkResponse.getJsonObject());
        } else {
            Toast.makeText(MainActivity.this, R.string.error_no_network, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                getMediaDetailFromServer();
                return true;

            case R.id.menu_sort_popular_movies:
                PopularMoviePreferences.setCurrentRequest(getApplicationContext(), 1);
                displayProgress();
                getMediaDetailFromServer();
                return true;

            case R.id.menu_sort_top_rating:
                PopularMoviePreferences.setCurrentRequest(getApplicationContext(), 2);
                displayProgress();
                getMediaDetailFromServer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Initialize views
     */
    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMovieList);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(
                getResources().getInteger(R.integer.span_column_view_for_movies),
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        progressBarLoader = (ProgressBar) findViewById(R.id.progressBarLoader);
        progressBarLoader.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    /**
     * Display Progress
     */
    private void displayProgress() {
        progressBarLoader.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }


    /**
     * Get media detail from servicer
     */
    private void getMediaDetailFromServer() {
        NetworkManager.NetworkCallRequest networkCallRequest = NetworkManager.NetworkCallRequest.NETWORK_CALL_REQUEST_POPULAR_MOVIES;
        if (PopularMoviePreferences.getCurrentRequest(getApplicationContext()) != 1) {
            networkCallRequest = NetworkManager.NetworkCallRequest.NETWORK_CALL_REQUEST_TOP_RATED;
        }
        NetworkManager.getInstance(getApplicationContext()).createNetworkRequest(
               networkCallRequest , this);
        updateSortTitle();
    }

    /**
     * Load the Media Detail List
     * @param responseObject MediaDetail list object
     */
    private void loadMediaDetail(JSONObject responseObject) {
        mMovieDetailsArrayList = MovieDataUtils.getMovieDetailsFromResponse(responseObject);
        mMovieListAdapter = new MovieListAdapter(getApplicationContext(), mMovieDetailsArrayList);
        mRecyclerView.setAdapter(mMovieListAdapter);
        updateListUI();
    }

    /**
     * Update List UI
     */
    private void updateListUI() {
        progressBarLoader.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mMovieListAdapter.notifyDataSetChanged();
        mRecyclerView.invalidate();
    }

    /**
     * Update the title
     */
    private void updateSortTitle() {
        if (PopularMoviePreferences.getCurrentRequest(getApplicationContext()) == 1) {
            setTitle(R.string.title_popular_movies_most_popular);
        } else {
            setTitle(R.string.title_popular_movies_highest_rated);
        }
    }

}
