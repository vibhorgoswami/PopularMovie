package com.govibs.popularmovie.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Popular Movie Preferences
 * Created by Vibhor on 11/30/16.
 */

public class PopularMoviePreferences {

    private static final String PREF_CURRENT_REQUEST = "pref_current_request";

    /**
     * Get preferences
     * @param context the calling application context.
     * @return Preferences
     */
    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set Current Request ID
     * @param context the calling application context.
     * @param currentRequestId the Current Request ID
     */
    public static void setCurrentRequest(Context context, int currentRequestId) {
        getPreferences(context).edit().putInt(PREF_CURRENT_REQUEST, currentRequestId).apply();
    }

    /**
     * Get the Current Request
     * @param context the the calling application context.
     * @return Request ID
     */
    public static int getCurrentRequest(Context context) {
        return getPreferences(context).getInt(PREF_CURRENT_REQUEST, 1);
    }

}
