package com.example.imagegallery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String KEY = "IMAGE";
    private ImageLoader imageLoader;
    private ArrayList<String> imageList = new ArrayList<>();
    private ArrayList<String> thumbnailList = new ArrayList<>();
    private GridView gridView;
    private ImageButton imageButton_delete, imageButton_undo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.GridView);

        imageList.add("https://i.ibb.co/6w1PD65/germany.png");
        imageList.add("https://i.ibb.co/Kqg7NSJ/indonesia.png");
        imageList.add("https://i.ibb.co/XD6Khqn/japan.png");
        imageList.add("https://i.ibb.co/cC37kdz/sarawak.png");
        imageList.add("https://i.ibb.co/zJ5MYBz/singapore.png");
        imageList.add("https://i.ibb.co/m4mKTdN/switzerland.png");

        thumbnailList = new ArrayList<>();
        thumbnailList.add("https://i.ibb.co/BZQXydr/germany-thumb.png");
        thumbnailList.add("https://i.ibb.co/Bz9vwjQ/indonesia-thumb.png");
        thumbnailList.add("https://i.ibb.co/8cyLz13/japan-thumb.png");
        thumbnailList.add("https://i.ibb.co/4ZMPgsT/sarawak-thumb.png");
        thumbnailList.add("https://i.ibb.co/2StxSD7/singapore-thumb.png");
        thumbnailList.add("https://i.ibb.co/1ryLLWp/switzerland-thumb.png");

        imageLoader = Singleton.getInstance(getApplicationContext()).getImageLoader();

        MyAdapter myAdapter = new MyAdapter(this, imageList, thumbnailList, imageLoader);
        gridView.setAdapter(myAdapter);

        imageButton_delete = findViewById(R.id.imageButton_delete);
        imageButton_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAdapter.delete();
            }
        });

        imageButton_undo = findViewById(R.id.imageButton_undo);
        imageButton_undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAdapter.undo();
            }
        });
    }
}