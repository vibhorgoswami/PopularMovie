package com.govibs.popularmovie.network;

import java.io.Serializable;

/**
 * Get the network response
 * Created by Vibhor on 11/30/16.
 */

public interface INetworkResponse extends Serializable {

    /**
     * Network Response
     * @param networkResponse get the {@link NetworkResponse}
     */
    void onNetworkResponse(NetworkResponse networkResponse);

}
