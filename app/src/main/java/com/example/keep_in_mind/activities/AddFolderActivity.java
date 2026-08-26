package com.example.keep_in_mind.activities;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.keep_in_mind.R;
import com.example.keep_in_mind.controllers.DatabaseCallback;
import com.example.keep_in_mind.controllers.DatabaseController;
import com.example.keep_in_mind.models.entities.Folder;
import com.example.keep_in_mind.models.entities.Project;
import com.example.keep_in_mind.models.entities.Type;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddFolderActivity extends AppCompatActivity {
    DatabaseController db;

    private EditText folder_name_input;
    private TextView start_text;
    private TextView end_text;
    private ChipGroup projects_list;

    private String start_date;
    private String end_date;
    private Button add_folder_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_folder);

        folder_name_input = findViewById(R.id.folder_name_input);
        projects_list = findViewById(R.id.projects_list);
        add_folder_btn = findViewById(R.id.add_folder_btn);
        db = DatabaseController.getInstance(this);
        

        add_folder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Folder folder = new Folder();
                folder.setName(folder_name_input.getText().toString());
                db.addFolder(folder, new DatabaseCallback<Long>() {
                    @Override
                    public void onResult(Long id) {
                        System.out.println("added new folder");
                        runOnUiThread(AddFolderActivity.this::finish);
                    }
                });
            }
        });

        loadChips();
    }

    private void loadChips(){
        DatabaseController db = DatabaseController.getInstance(this);
        db.getAllProjects(new DatabaseCallback<List<Project>>() {
            @Override
            public void onResult(List<Project> list) {
                runOnUiThread(() -> renderProjects(list));
            }
        });
    }

    private void renderProjects(List<Project> list) {
        projects_list.clearCheck();
        projects_list.removeAllViews();

        for (Project pj : list){
            String name = pj.getName();
            if (name == null || name.isEmpty()) {
                name = pj.getDescription();
            }
            if (name == null || name.isEmpty()) {
                name = "Unnamed Project (" + pj.getId() + ")";
            }
            Chip chip = new Chip(this);
            chip.setText(name);
            chip.setClickable(true);
            chip.setCheckable(true);
            projects_list.addView(chip);
        }
        System.out.println("added " + list.size() + " project chips");
    }

}