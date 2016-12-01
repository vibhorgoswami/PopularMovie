package com.govibs.popularmovie.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.govibs.popularmovie.R;

import org.json.JSONObject;

/**
 * This is the network manager to make all the network calls outside the application.
 * Created by Vibhor on 11/30/16.
 */

public final class NetworkManager {

    public enum NetworkCallRequest {
        NETWORK_CALL_REQUEST_POPULAR_MOVIES,
        NETWORK_CALL_REQUEST_TOP_RATED
    }

    private static NetworkManager mNetworkManager;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Context mContext;

    /**
     * Default Constructor
     */
    private NetworkManager(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    /**
     * Get the instance of the Network Manager
     * @return Network Manager
     */
    public synchronized static NetworkManager getInstance(Context context) {
        if (mNetworkManager == null) {
            mNetworkManager = new NetworkManager(context);
        }
        return mNetworkManager;
    }

    /**
     * Create Network Request
     * @param networkCallRequest the {@link NetworkCallRequest}
     * @param networkResponse the {@link INetworkResponse}
     */
    public void createNetworkRequest(NetworkCallRequest networkCallRequest,
                                     INetworkResponse networkResponse) {
        switch (networkCallRequest) {

            case NETWORK_CALL_REQUEST_POPULAR_MOVIES:
                makeRequestForPopularMovies(networkResponse);
                break;

            case NETWORK_CALL_REQUEST_TOP_RATED:
                makeRequestForTopRated(networkResponse);
                break;

        }
    }

    /**
     * Make a request for popular movies
     * @param networkResponse the {@link NetworkResponse}
     */
    private void makeRequestForPopularMovies(@NonNull final INetworkResponse networkResponse) {
        final Uri uri = Uri.parse(mContext.getString(R.string.base_url_movie_db)).buildUpon()
                .appendPath(mContext.getString(R.string.param_popular))
                .appendQueryParameter(mContext.getString(R.string.param_api),
                        mContext.getString(R.string.api_key)).build();
        Log.v("VIBS", uri.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(uri.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                final NetworkResponse response = new NetworkResponse();
                response.setJsonObject(jsonObject);
                networkResponse.onNetworkResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final NetworkResponse response = new NetworkResponse();
                response.setVolleyError(error);
                networkResponse.onNetworkResponse(response);
            }
        });
        addToRequestQueue(jsonObjectRequest);
    }

    /**
     * Make Request for Top Rated movied
     * @param networkResponse the {@link NetworkResponse}
     */
    private void makeRequestForTopRated(@NonNull final INetworkResponse networkResponse) {
        final Uri uri = Uri.parse(mContext.getString(R.string.base_url_movie_db)).buildUpon()
                .appendPath(mContext.getString(R.string.param_top_rated))
                .appendQueryParameter(mContext.getString(R.string.param_api),
                        mContext.getString(R.string.api_key)).build();
        Log.v("VIBS", uri.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(uri.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                final NetworkResponse response = new NetworkResponse();
                response.setJsonObject(jsonObject);
                networkResponse.onNetworkResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final NetworkResponse response = new NetworkResponse();
                response.setVolleyError(error);
                networkResponse.onNetworkResponse(response);
            }
        });
        addToRequestQueue(jsonObjectRequest);
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    private  <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
