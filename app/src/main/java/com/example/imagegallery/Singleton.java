package com.example.imagegallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class Singleton {
    private static Singleton mySingleton;
    private static Context context;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private Singleton(Context context) {
        Singleton.context = context;
        requestQueue = Volley.newRequestQueue(context);

        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(6);

            @Override
            public Bitmap getBitmap(String url) {
                Bitmap bitmap = cache.get(url);
                if(bitmap == null) { System.out.println("Image not found in cache"); }
                else { System.out.println("Image found in cache"); }
                return bitmap;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                System.out.println("Successfully put Bitmap");
                cache.put(url, bitmap);
            }
        });
    }

    public static synchronized Singleton getInstance(Context context) {
        if(mySingleton == null) { mySingleton = new Singleton(context); }
        return mySingleton;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
