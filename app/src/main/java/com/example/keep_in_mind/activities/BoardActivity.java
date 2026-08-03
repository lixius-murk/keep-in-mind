package com.example.keep_in_mind.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.keep_in_mind.R;
import com.example.keep_in_mind.controllers.DatabaseController;
import com.example.keep_in_mind.models.entities.Folder;
import com.example.keep_in_mind.models.entities.Project;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {
    private TextView ready_amnt_text;
    private TextView hold_amnt_text;
    private TextView process_amnt_text;
    private TextView finished_amnt_text;
    private LinearLayout folders_layout;
    private ScrollView folders_sv;
    private ImageButton add_folder_btn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        ready_amnt_text = (TextView) findViewById(R.id.ready_amnt_text);
        hold_amnt_text = (TextView) findViewById(R.id.hold_amnt_text);
        process_amnt_text = (TextView) findViewById(R.id.process_amnt_text);
        finished_amnt_text = (TextView) findViewById(R.id.finished_amnt_text);
        folders_layout = (LinearLayout) findViewById(R.id.folders_layout);
        folders_sv = (ScrollView) findViewById(R.id.folders_sv);
        add_folder_btn = (ImageButton) findViewById(R.id.add_folder_btn);

        add_folder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BoardActivity.this, AddFolderActivity.class));
            }
        });

        loadProjects();
    }

    private void loadProjects() {
        DatabaseController db = DatabaseController.getInstance(this);
        db.getAllFolders(folder -> runOnUiThread(() -> renderFolders(folder)));
    }

    private void renderFolders(List<Folder> folders) {
        folders_sv.removeAllViews();

        if (folders.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("no projects yet");
            folders_sv.addView(empty);
            return;
        }

        for (Folder folder : folders) {
            TextView projectText = new TextView(this);
            projectText.setText(folder.getName());
            folders_sv.addView(projectText);
        }
    }

}