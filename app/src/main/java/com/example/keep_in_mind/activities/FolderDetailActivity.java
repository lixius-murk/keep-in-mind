package com.example.keep_in_mind.activities;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keep_in_mind.R;
import com.example.keep_in_mind.adapters.ProjectAdapter;
import com.example.keep_in_mind.controllers.DatabaseCallback;
import com.example.keep_in_mind.controllers.DatabaseController;
import com.example.keep_in_mind.models.entities.Project;

import java.util.List;

public class FolderDetailActivity extends AppCompatActivity {

    private TextView folder_name;
    private RecyclerView projects_rv;
    private ImageButton close_btn;
    private DatabaseController db;
    private ProjectAdapter adapter;
    private Long folderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_detail);

        folderId = getIntent().getLongExtra("folder_id", -1);
        String folderName = getIntent().getStringExtra("folder_name");

        folder_name = findViewById(R.id.folder_name);
        projects_rv = findViewById(R.id.projects_rv);
        close_btn = findViewById(R.id.close_btn);

        if (folderName != null) {
            folder_name.setText(folderName);
        }

        adapter = new ProjectAdapter(project -> {
            // TODO: navigate to a project-detail screen once that Activity
            // exists. Left as a no-op placeholder for now.
            System.out.println("clicked project id: " + project.getId());
        });
        projects_rv.setLayoutManager(new LinearLayoutManager(this));
        projects_rv.setAdapter(adapter);

        db = DatabaseController.getInstance(this);
        close_btn.setOnClickListener(v -> finish());

        if (folderId != -1) {
            loadProjects();
        }
    }

    private void loadProjects() {
        db.getProjectsByFolder(folderId, new DatabaseCallback<List<Project>>() {
            @Override
            public void onResult(List<Project> list) {
                runOnUiThread(() -> adapter.submitList(list));
            }
        });
    }
}