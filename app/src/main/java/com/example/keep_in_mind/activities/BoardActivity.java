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
import com.example.keep_in_mind.controllers.DatabaseCallback;
import com.example.keep_in_mind.controllers.DatabaseController;
import com.example.keep_in_mind.models.entities.Folder;

import java.util.List;

public class BoardActivity extends AppCompatActivity {
    private TextView ready_amnt_text;
    private TextView hold_amnt_text;
    private TextView process_amnt_text;
    private TextView finished_amnt_text;
    private LinearLayout folders_layout;
    private ScrollView folders_sv;
    private ImageButton add_folder_btn;

    private LinearLayout folders_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

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

        loadFolders();
    }

    private void loadFolders() {
        DatabaseController db = DatabaseController.getInstance(this);
        db.getAllFolders(new DatabaseCallback<List<Folder>>() {
            @Override
            public void onResult(List<Folder> folders) {
                runOnUiThread(() -> renderFolders(folders));
            }
        });
    }

    private void renderFolders(List<Folder> folders) {
        folders_container.removeAllViews();

        int marginPx = (int) (40 * getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.leftMargin = marginPx;

        if (folders.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("no folders yet");
            empty.setTextAppearance(R.style.folderItem);
            empty.setLayoutParams(params);
            folders_container.addView(empty);
            return;
        }

        for (Folder folder : folders) {
            TextView folderText = new TextView(this);
            folderText.setText(folder.getName());
            folderText.setClickable(true);
            folderText.setLayoutParams(params);
            folderText.setTextAppearance(R.style.folderItem);

            folderText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BoardActivity.this, FolderDetailActivity.class);
                    intent.putExtra("folder_id", folder.getId());
                    startActivity(intent);
                }
            });

            folders_container.addView(folderText);
            System.out.println("added folder: " + folder.getName());
        }
    }
}