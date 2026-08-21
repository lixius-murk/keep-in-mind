package com.example.keep_in_mind;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ProjectFragment extends Fragment {

    private static final String ARG_PROJECT_NAME = "project_name";
    private String projectName;

    public ProjectFragment() {
    }

    public static ProjectFragment newInstance(String name) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PROJECT_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }
    public String getName(){
        return projectName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            projectName = getArguments().getString(ARG_PROJECT_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        TextView projectNameView = view.findViewById(R.id.project_name);
        if (projectName != null) {
            projectNameView.setText(projectName);
        }
        return view;
    }
}