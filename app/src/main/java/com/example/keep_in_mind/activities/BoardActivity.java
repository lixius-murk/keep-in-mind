package com.example.keep_in_mind.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.keep_in_mind.R;
import com.example.keep_in_mind.controllers.DatabaseController;
import com.example.keep_in_mind.models.entities.Folder;
import com.example.keep_in_mind.models.entities.Project;

import java.util.List;

public class BoardActivity extends AppCompatActivity {
    private TextView ready_amnt_text;
    private TextView hold_amnt_text;
    private TextView process_amnt_text;
    private TextView finished_amnt_text;
    private LinearLayout folders_layout;
    private ScrollView folders_sv;
    private ImageButton add_folder_btn;
    private ImageButton add_proj_btn;


    public static final String[] states = {"ready", "on hold", "in progress", "finished"};


    private LinearLayout folders_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        add_proj_btn = findViewById(R.id.add_proj_btn);
        ready_amnt_text = findViewById(R.id.ready_amnt_text);
        hold_amnt_text = findViewById(R.id.hold_amnt_text);
        process_amnt_text = findViewById(R.id.process_amnt_text);
        finished_amnt_text = findViewById(R.id.finished_amnt_text);
        folders_container = findViewById(R.id.folders_container);
        folders_sv = findViewById(R.id.folders_sv);
        add_folder_btn = findViewById(R.id.add_folder_btn);


        add_folder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BoardActivity.this, AddFolderActivity.class));
            }
        });
        add_proj_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BoardActivity.this, AddProjectActivity.class));

            }
        });

        observeData();
    }

    private void observeData() {
        DatabaseController db = DatabaseController.getInstance(this);
        
        db.getAllFoldersLive().observe(this, folders -> {
            renderFolders(folders);
        });

        db.getAllProjectsLive().observe(this, projects -> {
            int ready = 0, hold = 0, process = 0, finished = 0;
            for (Project p : projects) {
                String state = p.getState() != null ? p.getState().toLowerCase() : "";
                //if-else bc Java switch statements require constant expressions for case labels
                if (states[0].equals(state)) ready++;
                else if (states[1].equals(state)) hold++;
                else if (states[2].equals(state)) process++;
                else if (states[3].equals(state)) finished++;
                else System.out.println("no state for project chosen!!");
            }
            updateStatsUi(ready, hold, process, finished);
        });
    }

    private void updateStatsUi(int ready, int hold, int process, int finished) {
        ready_amnt_text.setText(ready + (ready == 1 ? " task" : " tasks"));
        hold_amnt_text.setText(hold + (hold == 1 ? " task" : " tasks"));
        process_amnt_text.setText(process + (process == 1 ? " task" : " tasks"));
        finished_amnt_text.setText(finished + (finished == 1 ? " task" : " tasks"));
    }

    private void renderFolders(List<Folder> folders) {
        folders_container.removeAllViews();
        for (Folder fd : folders) {
            View itemView = getLayoutInflater().inflate(R.layout.item_folder, folders_container, false);
            TextView tv = itemView.findViewById(R.id.folder_name_item);
            tv.setText(fd.getName());

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(BoardActivity.this, FolderDetailActivity.class);
                intent.putExtra("folder_id", fd.getId());
                intent.putExtra("folder_name", fd.getName());
                startActivity(intent);
            });

            folders_container.addView(itemView);
        }
    }
}