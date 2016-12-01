package com.govibs.popularmovie.network;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Network response object
 * Created by Vibhor on 11/30/16.
 */

public final class NetworkResponse {

    private String networkError;
    private VolleyError volleyError;
    private JSONObject jsonObject;
    private JSONArray jsonArray;


    public String getNetworkError() {
        return networkError;
    }

    protected void setNetworkError(String networkError) {
        this.networkError = networkError;
    }

    public VolleyError getVolleyError() {
        return volleyError;
    }

    protected void setVolleyError(VolleyError volleyError) {
        this.volleyError = volleyError;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    protected void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    protected void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }
}
