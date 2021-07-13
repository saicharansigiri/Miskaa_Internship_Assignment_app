package com.example.miskaaassignment.utils;


import android.content.Context;
import android.widget.ImageView;

import com.example.miskaaassignment.R;
import com.pixplicity.sharp.Sharp;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Utils {
    private  static OkHttpClient httpClient;

    // this method is used to fetch svg and load it into target imageview.
    public static void fetchSvg(Context context, String url, final ImageView target) {

        if (httpClient == null) {
            httpClient = new OkHttpClient.Builder()
                    .cache(new Cache(context.getCacheDir(), 5 * 1024 * 1014))
                    .build();
        }

        // here we are making HTTP call to fetch data from URL.
        Request request = new Request.Builder().url(url).build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // we are adding a default image if we gets any error.
                target.setImageResource(R.drawable.ic_baseline_image_not_supported_24);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                InputStream stream = response.body().byteStream();
                Sharp.loadInputStream(stream).into(target);
                //Log.e("BYTE", stream.toString());

                stream.close();
            }
        });
    }


}
