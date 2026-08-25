package com.example.keep_in_mind.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.keep_in_mind.R;


public class TabFragment extends Fragment {
    private static final String ARG_TYPE = "type";
    private String mParam1;
    private Button tab_btn;

    public TabFragment() {
        // Required empty public constructor
    }


    public String getType() { return "base";}

    public static TabFragment newInstance(String param1, String param2) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tab_btn = view.findViewById(R.id.tab_btn);
        if (tab_btn != null) {
            tab_btn.setText(getType());
            tab_btn.setOnClickListener(v -> {
                // Handle button click if needed
            });
        }
    }

}