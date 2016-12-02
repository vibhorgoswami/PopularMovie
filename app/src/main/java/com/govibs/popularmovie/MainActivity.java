package com.govibs.popularmovie;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.govibs.popularmovie.movie.IMovieDetailCallback;
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

    private static final int REQUEST_POPULAR_MOVIES = 0;
    private static final int REQUEST_HIGHEST_RATED = 1;
    private static final int REQUES_MOVIE_DETAILS = 2;

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private RelativeLayout mContainerView;
    private MovieListAdapter mMovieListAdapter;
    private ArrayList<MovieDetails> mMovieDetailsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

            case R.id.menu_sort:
                showSortDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Initialize views
     */
    private void initViews() {
        mContainerView = (RelativeLayout) findViewById(R.id.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMovieList);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(
                getResources().getInteger(R.integer.span_column_view_for_movies),
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMovieListAdapter = new MovieListAdapter(MainActivity.this,
                mMovieDetailsArrayList, new IMovieDetailCallback() {
            @Override
            public void onMovieSelected(int position) {
                startMovieDetailActivity(mMovieDetailsArrayList.get(position));
            }
        });
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setAdapter(mMovieListAdapter);
    }


    /**
     * Get media detail from service
     */
    private void getMediaDetailFromServer() {
        NetworkManager.NetworkCallRequest networkCallRequest = NetworkManager.NetworkCallRequest.NETWORK_CALL_REQUEST_POPULAR_MOVIES;
        if (PopularMoviePreferences.getCurrentRequest(getApplicationContext()) == REQUEST_HIGHEST_RATED) {
            networkCallRequest = NetworkManager.NetworkCallRequest.NETWORK_CALL_REQUEST_TOP_RATED;
        }
        NetworkManager.getInstance(getApplicationContext()).createNetworkRequest(
               networkCallRequest , this);
    }

    /**
     * Load the Media Detail List
     * @param responseObject MediaDetail list object
     */
    private void loadMediaDetail(JSONObject responseObject) {
        mMovieListAdapter.swapData(MovieDataUtils.getMovieDetailsFromResponse(responseObject));
        updateListUI();
    }

    /**
     * Update List UI
     */
    private void updateListUI() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.invalidate();
        mContainerView.invalidate();
        updateSortTitle();
    }

    /**
     * Update the title
     */
    private void updateSortTitle() {
        if (PopularMoviePreferences.getCurrentRequest(getApplicationContext()) == REQUEST_POPULAR_MOVIES) {
            setTitle(R.string.title_popular_movies_most_popular);
        } else {
            setTitle(R.string.title_popular_movies_highest_rated);
        }
    }

    /**
     * Show sort dialog.
     */
    private void showSortDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort");
        int checkedItem = PopularMoviePreferences.getCurrentRequest(getApplicationContext());
        builder.setSingleChoiceItems(R.array.sort_options, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    PopularMoviePreferences.setCurrentRequest(getApplicationContext(), REQUEST_POPULAR_MOVIES);
                    getMediaDetailFromServer();
                } else {
                    PopularMoviePreferences.setCurrentRequest(getApplicationContext(), REQUEST_HIGHEST_RATED);
                    getMediaDetailFromServer();
                }
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void startMovieDetailActivity(MovieDetails movieDetails) {
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.PARAM_MOVIE_DETAIL, movieDetails);
        startActivityForResult(intent, REQUES_MOVIE_DETAILS);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

}
