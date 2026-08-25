package com.example.keep_in_mind.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.keep_in_mind.R;


import android.widget.LinearLayout;
import android.widget.TextView;

public class LinkTabFragment extends TabFragment {
    private static final String ARG_LINK_LOCATION = "link_location";
    private String link_location;

    public LinkTabFragment() {
        // Required empty public constructor
    }

    public static LinkTabFragment newInstance(String content) {
        LinkTabFragment fragment = new LinkTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LINK_LOCATION, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            link_location = getArguments().getString(ARG_LINK_LOCATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_link_tab, container, false);
        LinearLayout linksContainer = root.findViewById(R.id.links_container);

        if (link_location != null && !link_location.isEmpty()) {
            for (String url : link_location.split("\n")) {
                String trimmedUrl = url.trim();
                if (trimmedUrl.isEmpty()) continue;

                View itemView = inflater.inflate(R.layout.item_link, linksContainer, false);
                TextView linkText = itemView.findViewById(R.id.link_text);
                linkText.setText(trimmedUrl);
                
                linkText.setOnClickListener(v -> {
                    try {
                        String formattedUrl = trimmedUrl;
                        if (!formattedUrl.startsWith("http://") && !formattedUrl.startsWith("https://")) {
                            formattedUrl = "http://" + formattedUrl;
                        }
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(formattedUrl));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                linksContainer.addView(itemView);
            }
        }

        return root;
    }
    @Override
    public String getType(){
        return "link";
    }
}