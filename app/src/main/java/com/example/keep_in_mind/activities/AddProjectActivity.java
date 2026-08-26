package com.example.keep_in_mind.activities;



import static com.example.keep_in_mind.R.id.extra_chips;
import static com.example.keep_in_mind.R.id.finished_amnt_text;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.keep_in_mind.R;
import com.example.keep_in_mind.controllers.DatabaseCallback;
import com.example.keep_in_mind.controllers.DatabaseController;
import com.example.keep_in_mind.models.entities.Project;
import com.example.keep_in_mind.models.entities.Type;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class AddProjectActivity extends AppCompatActivity {


    private  OnDatePicked onDatePickerStart = null;
    private  OnDatePicked onDatePickerEnd = null;
    private static final String DATE_FORMAT = "%04d-%02d-%02d";
    private ChipGroup extra_chips;
    private DatabaseController db;
    private Button add_project_btn;
    private TextView start_text;
    private TextView end_text;

    private String date_st;
    private String date_end;

    private EditText projectNameInput;
    private Long folderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        
        folderId = getIntent().getLongExtra("folder_id", -1);
        
        projectNameInput = findViewById(R.id.folder_name_input);
        extra_chips = findViewById(R.id.extra_chips);
        add_project_btn = findViewById(R.id.add_project_btn);
        start_text = findViewById(R.id.start_text);
        end_text = findViewById(R.id.end_text);
        db = DatabaseController.getInstance(this);

        start_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDatePickerStart = formatted -> date_st = formatted;
                showDatePicker(start_text, onDatePickerStart);
            }
        });
        end_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDatePickerEnd = formatted -> date_end = formatted;
                showDatePicker(end_text, onDatePickerEnd);
            }
        });
        add_project_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = projectNameInput.getText().toString();
                Project pj = new Project();
                pj.setName(inputName);
                pj.setDescription(" ");
                pj.setStart_date(date_st);
                pj.setEnd_date(date_end);
                if (folderId != -1) {
                    pj.setFolder_id(folderId);
                }
                pj.setState("ready");
                db.addProject(pj, new DatabaseCallback<Long>() {
                    @Override
                    public void onResult(Long id) {
                        System.out.println("added new project: " + inputName);
                        runOnUiThread(AddProjectActivity.this::finish);
                    }});
            }
        });

        loadChips();
    }


    private void showDatePicker(TextView target, AddProjectActivity.OnDatePicked onDatePicked) {
        System.out.println("tried opening data picker");
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String formatted = String.format(Locale.US, DATE_FORMAT, year, month + 1, dayOfMonth);
                    target.setText(formatted);
                    onDatePicked.onDatePicked(formatted);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        System.out.println("openned data picker");

        dialog.show();
    }

    private interface OnDatePicked {
        void onDatePicked(String date);
    }
    private void loadChips(){
        DatabaseController db = DatabaseController.getInstance(this);
        db.getAllTypes(list -> runOnUiThread(() -> renderTypes(list)));
    }

    private void renderTypes(List<Type> list) {
        extra_chips.clearCheck();
        for (Type type : list){
            Chip chip = new Chip(this);
            chip.setText(type.getName());
            chip.setClickable(true);
            chip.setCheckable(true);
            extra_chips.addView(chip);
        }
        System.out.println("added type chip");
    }
}