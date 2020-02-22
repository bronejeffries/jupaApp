package com.example.jupa.Helpers;

import android.content.Context;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;

public class PicassoSingleton {

    private static Picasso picasso;

    public static Picasso getPicassoInstance(Context context) {

        if (picasso == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                    .build();

            picasso = new Picasso.Builder(context)
                    .downloader(new OkHttp3Downloader(client))
                    .loggingEnabled(true)
                    .build();

        }

        return picasso;
    }

}
