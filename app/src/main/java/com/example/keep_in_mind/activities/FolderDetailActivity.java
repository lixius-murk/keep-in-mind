package com.example.keep_in_mind.activities;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.keep_in_mind.ProjectFragment;
import com.example.keep_in_mind.R;
import com.example.keep_in_mind.controllers.DatabaseCallback;
import com.example.keep_in_mind.controllers.DatabaseController;
import com.example.keep_in_mind.models.entities.Project;

import java.util.List;

public class FolderDetailActivity extends AppCompatActivity {

    private TextView folder_name;
    private LinearLayout projectsContainer;
    private ImageButton close_btn;
    private DatabaseController db;
    private Long folderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_detail);

        // Get folder ID from intent
        folderId = getIntent().getLongExtra("folder_id", -1);
        String folderName = getIntent().getStringExtra("folder_name");

        folder_name = findViewById(R.id.folder_name);
        projectsContainer = findViewById(R.id.projects_container);
        close_btn = findViewById(R.id.close_btn);

        // Set folder name
        if (folderName != null) {
            folder_name.setText(folderName);
        }

        db = DatabaseController.getInstance(this);

        close_btn.setOnClickListener(v -> finish());

        // Load projects
        if (folderId != -1) {
            loadProjects();
        }
    }

    private void loadProjects() {
        db.getProjectsByFolder(folderId, new DatabaseCallback<List<Project>>() {
            @Override
            public void onResult(List<Project> list) {
                runOnUiThread(() -> renderProjects(list));
            }

            @Override
            public void onError(Exception e) {
                // Handle error
                e.printStackTrace();
            }
        });
    }

    private void renderProjects(List<Project> list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        for (Project project : list) {
            ProjectFragment fragment = ProjectFragment.newInstance(project.getName());
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.projects_container, fragment);
            transaction.commit();
        }
    }
}