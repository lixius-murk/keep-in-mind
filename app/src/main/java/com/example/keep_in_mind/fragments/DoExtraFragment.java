package com.example.keep_in_mind.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.keep_in_mind.R;


public class DoExtraFragment extends Fragment {

    private static final String[] ARG_LIST= {};

    private String[] list;

    public DoExtraFragment() {
    }

    public static DoExtraFragment newInstance(String param1) {
        DoExtraFragment fragment = new DoExtraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LIST, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_do_extra, container, false);
    }
}