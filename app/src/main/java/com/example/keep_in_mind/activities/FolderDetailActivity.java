package com.example.keep_in_mind.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keep_in_mind.R;
import com.example.keep_in_mind.adapters.ProjectAdapter;
import com.example.keep_in_mind.controllers.DatabaseCallback;
import com.example.keep_in_mind.controllers.DatabaseController;
import com.example.keep_in_mind.models.entities.Project;

import java.util.ArrayList;
import java.util.List;

public class FolderDetailActivity extends AppCompatActivity {


    private TextView folder_name;
    private RecyclerView projects_rv;
    private ImageButton close_btn;
    private ImageButton add_btn;
    private DatabaseController db;
    private ProjectAdapter adapter;
    private Long folderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_detail);
        db = DatabaseController.getInstance(this);

        folderId = getIntent().getLongExtra("folder_id", -1);
        String folderName = getIntent().getStringExtra("folder_name");


        add_btn = findViewById(R.id.add_btn);
        close_btn = findViewById(R.id.close_btn);
        folder_name = findViewById(R.id.folder_name);
        projects_rv = findViewById(R.id.projects_rv);

        if (folderName != null) {
            folder_name.setText(folderName);
        }

        add_btn.setOnClickListener(v -> popupAddProject(v));
        close_btn.setOnClickListener(v -> finish());


        adapter = new ProjectAdapter(project -> {
            Intent i = new Intent(FolderDetailActivity.this, ProjectDetailsActivity.class);
            i.putExtra("project_id", project.getId());
            startActivity(i);
        });
        projects_rv.setLayoutManager(new LinearLayoutManager(this));
        projects_rv.setAdapter(adapter);

        if (folderId != -1) {
            observeProjects();
        }
    }

    private void observeProjects() {
        db.getProjectsByFolderLive(folderId).observe(this, list -> {
            adapter.submitList(list);
        });
    }

    public void popupAddProject(View view) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.project_to_folder, null);
        Button new_btn = popupView.findViewById(R.id.new_btn);
        Button old_btn = popupView.findViewById(R.id.old_btn);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        new_btn.setOnClickListener(v -> {
            popupWindow.dismiss();
            Intent i = new Intent(FolderDetailActivity.this, AddProjectActivity.class);
            i.putExtra("folder_id", folderId);
            startActivity(i);
        });
        old_btn.setOnClickListener(v -> {
            popupWindow.dismiss();
            chooseProjects();
        });
    }

    private void chooseProjects() {
        db.getAllProjects(new DatabaseCallback<List<Project>>() {
            @Override
            public void onResult(List<Project> projects) {
                List<Project> availableProjects = new ArrayList<>();
                for (Project p : projects) {
                    if (p.getFolder_id() == null || !p.getFolder_id().equals(folderId)) {
                        availableProjects.add(p);
                    }
                }

                if (availableProjects.isEmpty()) {
                    runOnUiThread(() -> {
                        new AlertDialog.Builder(FolderDetailActivity.this)
                                .setTitle("No projects")
                                .setMessage("No existing projects found to add.")
                                .setPositiveButton("OK", null)
                                .show();
                    });
                    return;
                }

                String[] projectNames = new String[availableProjects.size()];
                boolean[] selectedItems = new boolean[availableProjects.size()];
                for (int i = 0; i < availableProjects.size(); i++) {
                    Project p = availableProjects.get(i);
                    String name = p.getName();
                    if (name == null || name.isEmpty()) {
                        name = p.getDescription();
                    }
                    if (name == null || name.isEmpty()) {
                        name = "Unnamed Project (" + p.getId() + ")";
                    }
                    projectNames[i] = name;
                }

                runOnUiThread(() -> {
                    new AlertDialog.Builder(FolderDetailActivity.this)
                            .setTitle("Select projects to add")
                            .setMultiChoiceItems(projectNames, selectedItems, (dialog, which, isChecked) -> {
                                selectedItems[which] = isChecked;
                            })
                            .setPositiveButton("Add", (dialog, which) -> {
                                for (int i = 0; i < selectedItems.length; i++) {
                                    if (selectedItems[i]) {
                                        Project p = availableProjects.get(i);
                                        p.setFolder_id(folderId);
                                        db.updateProject(p, result -> {});
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                });
            }
        });
    }

}

