package com.example.imagegallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> imageList;
    private ArrayList<String> thumbnailList;
    private HashMap<String,String> RecycleBin = new HashMap<>();
    private ArrayList<Integer> selectedImage = new ArrayList<>();
    private ImageLoader imageLoader;

    public MyAdapter(Context context, ArrayList<String> imageLarge_List, ArrayList<String> imageThumb_List, ImageLoader imageLoader) {
        this.context = context;
        this.imageList = imageLarge_List;
        this.thumbnailList = imageThumb_List;
        this.imageLoader = imageLoader;
    }


    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int i) {
        return thumbnailList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater.from(context));
        view = layoutInflater.inflate(R.layout.thumbnail_layout, null);

        // initialize ui
        final ImageView thumbnail = view.findViewById(R.id.imageView_thumbnail);
        CheckBox checkBox = view.findViewById(R.id.checkbox);

        imageLoader.get(thumbnailList.get(i), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if(response.getBitmap()!=null) {
                    Bitmap bmp = response.getBitmap();
                    thumbnail.setImageBitmap(bmp);
                }
            }
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",error.getMessage());
            }
        });

        thumbnail.setTag(i);

        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = Integer.parseInt(view.getTag().toString());
                Intent intent = new Intent(view.getContext(), MainActivity2.class);
                intent.putExtra(MainActivity.KEY, imageList.get(pos));
                view.getContext().startActivity(intent);
            }
        });

        selectedImage.clear();
        checkBox.setTag(i);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean clicked) {
                int pos = Integer.parseInt(compoundButton.getTag().toString());
                if (clicked) { selectedImage.add(pos); }
                else { selectedImage.remove(Integer.valueOf(pos)); }
            }
        });

        return view;
    }

    public void delete() {
        if (!selectedImage.isEmpty()) {
            Collections.sort(selectedImage, Collections.reverseOrder());
            for (int i = 0; i < selectedImage.size(); i++) {
                int index = selectedImage.get(i);
                RecycleBin.put(imageList.get(index), thumbnailList.get(index));
                imageList.remove(index);
                thumbnailList.remove(index);
                notifyDataSetChanged();
            }
        }
    }

    public void undo() {
        if(!RecycleBin.isEmpty()) {
            for (String key: RecycleBin.keySet()) {
                imageList.add(key);
                thumbnailList.add(RecycleBin.get(key));
            }
            RecycleBin.clear();
            notifyDataSetChanged();
        }
    }
}
