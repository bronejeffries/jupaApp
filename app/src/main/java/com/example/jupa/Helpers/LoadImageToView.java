package com.example.jupa.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.jupa.R;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;

import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;

import static com.example.jupa.Helpers.LogUtils.TAG;

public class LoadImageToView {


    private static final String IMAGE_BASE_URL = "https://jupa.maganjoinstitute.com/dashboard/img/";

    public static void loadImage(Context context, String url, final ImageView view){


//        TODO : fetch image by url and load to view
//        if (url!=null){
//
//            String path = IMAGE_BASE_URL+"P4.png";
//            Log.e(TAG, "loadImage: "+path);
//
//                    PicassoSingleton.getPicassoInstance(context).load(path)
//                    .placeholder(R.drawable.construction_icon_png_5)
//                    .memoryPolicy(MemoryPolicy.NO_CACHE)
//                    .networkPolicy(NetworkPolicy.NO_CACHE)
//                    .resize(250,250)
//                    .into(view);
//        }

        if (url!=null){
//            imageprogress.setVisibility(View.VISIBLE);

            String path = IMAGE_BASE_URL+url;
            Log.e(TAG, "loadImage: "+path);
            ImageRequest imageRequest = new ImageRequest(
                    path,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
//                            btmap = response;
                                view.setImageBitmap(response);
//                            imageprogress.setVisibility(View.GONE);
                        }
                    }, 205, 205,
                    ImageView.ScaleType.CENTER_CROP,
                    null,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
            VolleySingleton.getInstance(context).addToRequestQueue(imageRequest);

        }

    }

    public static void setAsBackground(final Context context,String url, final View view){

//        if (url!=null){
//
//            Log.e(TAG, "background loadImage: "+url);
//            ImageView imageView = new ImageView(context);
//            PicassoSingleton.getPicassoInstance(context)
//                    .load(IMAGE_BASE_URL+"P4.png")
//                    .memoryPolicy(MemoryPolicy.NO_CACHE)
//                    .networkPolicy(NetworkPolicy.NO_CACHE).into(imageView);
//
//            Drawable drawable = imageView.getDrawable();
//            view.setBackground(drawable);
//        }

        if (url!=null){
//            imageprogress.setVisibility(View.VISIBLE);

            String path = IMAGE_BASE_URL+url;
            Log.e(TAG, "setAsBackground: "+path);
            ImageRequest imageRequest = new ImageRequest(
                    path,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
//                            btmap = response;
                            ImageView imageView = new ImageView(context);
                            imageView.setImageBitmap(response);
                            Drawable drawable = imageView.getDrawable();
                            view.setBackground(drawable);
//                            imageprogress.setVisibility(View.GONE);
                        }
                    }, 255, 370,
                    ImageView.ScaleType.CENTER_CROP,
                    null,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
            VolleySingleton.getInstance(context).addToRequestQueue(imageRequest);

        }

    }

}
