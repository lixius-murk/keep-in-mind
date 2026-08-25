package com.example.keep_in_mind.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.keep_in_mind.R;

import java.io.File;

public class MoodboardImageFragment extends TabFragment {
    private static final String ARG_FOLDER_LOCATION = "folder_location";
    private String folder_location;
    private File dir;


    public MoodboardImageFragment() {
        // Required empty public constructor
    }

    public static MoodboardImageFragment newInstance(String content) {
        MoodboardImageFragment fragment = new MoodboardImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FOLDER_LOCATION, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            folder_location = getArguments().getString(ARG_FOLDER_LOCATION);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_moodboard_tab, container, false);
        LinearLayout imgContainer = root.findViewById(R.id.images_container);
        if (dir != null && dir.isDirectory()) {
            File[] files = dir.listFiles(ImageTabFragment.IMAGE_FILTER);
            if (files != null) {
                for (final File f : files) {
                    Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                    if (bitmap == null) continue;
                    View itemView = inflater.inflate(R.layout.item_image, imgContainer, false);
                    ImageView imageView = itemView.findViewById(R.id.img_view);
                    imageView.setImageBitmap(bitmap);
                    imgContainer.addView(itemView);
                }
            }
        }
        return root;
    }
    private boolean isImageFile(String name) {
        String lower = name.toLowerCase();
        return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png") || lower.endsWith(".webp");
    }

    @Override
    public String getType(){
        return "moodboard";
    }
}