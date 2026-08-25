package com.example.keep_in_mind.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.keep_in_mind.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;


public class ImageTabFragment extends TabFragment {
    private static final String ARG_FOLDER_LOCATION = "folder_location";
    private String folder_location;
    private File dir;
    static final String[] EXTENSIONS = new String[]{
            "gif", "png", "bmp", "jpg", "webp"
    };


    // filter for image files
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };


    public ImageTabFragment() {
        // Required empty public constructor
    }

    public static ImageTabFragment newInstance(String content) {
        ImageTabFragment fragment = new ImageTabFragment();
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
         dir = new File(folder_location);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_image_tab, container, false);
        LinearLayout imgContainer = root.findViewById(R.id.images_container);
        if (dir != null && dir.isDirectory()) {
            File[] files = dir.listFiles(IMAGE_FILTER);
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
        return "ref";
    }
}