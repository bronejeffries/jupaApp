package com.example.jupa.Helpers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

/**
 * Created by bronej on 11/11/18.
 */

public class VolleySingleton {

    private static VolleySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mcxt;
    private VolleySingleton(Context context){

        mcxt = context;
        requestQueue = getRequestQueue();

    }

    public RequestQueue getRequestQueue(){

        if (requestQueue == null){

            requestQueue = Volley.newRequestQueue(mcxt.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized VolleySingleton getInstance(Context context){


        if (mInstance==null) {

            mInstance = new VolleySingleton(context);

        }
        return mInstance;
    }

    public<T> void addToRequestQueue(Request<T> request){
        request.setRetryPolicy(
                new RetryPolicy() {
                    @Override
                    public int getCurrentTimeout() {

                        return 3*60*1000;

                    }

                    @Override
                    public int getCurrentRetryCount() {
                        return 2;
                    }

                    @Override
                    public void retry(VolleyError error) throws VolleyError {

                        error.printStackTrace();

                    }
                }
        );

        requestQueue.add(request);
    }


}
