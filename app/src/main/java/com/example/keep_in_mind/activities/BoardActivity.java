package com.example.keep_in_mind.activities;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.keep_in_mind.R;

public class BoardActivity extends AppCompatActivity {
    private TextView ready_amnt_text;
    private TextView hold_amnt_text;
    private TextView process_amnt_text;
    private TextView finished_amnt_text;
    private LinearLayout folders_layout;
    private ScrollView folders_sv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        ready_amnt_text=(TextView)findViewById(R.id.ready_amnt_text);
        hold_amnt_text=(TextView)findViewById(R.id.hold_amnt_text);
        process_amnt_text=(TextView)findViewById(R.id.process_amnt_text);
        finished_amnt_text=(TextView)findViewById(R.id.finished_amnt_text);
        folders_layout= (LinearLayout) findViewById(R.id.folders_layout);
        folders_sv= (ScrollView) findViewById(R.id.folders_sv);
    }



}