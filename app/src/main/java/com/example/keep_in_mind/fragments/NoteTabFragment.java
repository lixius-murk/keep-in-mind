package com.example.keep_in_mind.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.keep_in_mind.R;


public class NoteTabFragment extends TabFragment {
    private static final String ARG_NOTE_LOCATION = "note_location";
    private String note_location;
    public NoteTabFragment() {
        // Required empty public constructor
    }
    public static NoteTabFragment newInstance(String content) {
        NoteTabFragment fragment = new NoteTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NOTE_LOCATION, content);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note_location = getArguments().getString(ARG_NOTE_LOCATION);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_note_tab, container, false);
        LinearLayout linksContainer = root.findViewById(R.id.notes_container);

        if (note_location != null && !note_location.isEmpty()) {
            for (String note : note_location.split("\n")) {
                if (note.isEmpty()) continue;

                View itemView = inflater.inflate(R.layout.item_note, linksContainer, false);
                TextView linkText = itemView.findViewById(R.id.note_text);
                linkText.setText(note);

                linkText.setOnClickListener(v -> {
                    //open note
                });

                linksContainer.addView(itemView);
            }
        }

        return root;
    }
    @Override
    public String getType(){
        return "note";
    }

}