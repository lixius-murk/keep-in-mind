package com.example.keep_in_mind.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.keep_in_mind.R;
import com.example.keep_in_mind.controllers.DatabaseCallback;
import com.example.keep_in_mind.controllers.DatabaseController;
import com.example.keep_in_mind.models.entities.Project;
import com.example.keep_in_mind.models.entities.ProjectExtra;
import com.example.keep_in_mind.models.entities.Type;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddProjectActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String selectedState = BoardActivity.states[0];
    private OnDatePicked onDatePickerStart = null;
    private OnDatePicked onDatePickerEnd = null;
    private static final String DATE_FORMAT = "%04d-%02d-%02d";
    private ChipGroup extra_chips;
    private DatabaseController db;
    private Spinner spinner;
    private Button add_project_btn;
    private TextView start_text;
    private TextView end_text;

    private String date_st;
    private String date_end;

    private EditText projectNameInput;
    private Long folderId;
    private List<Type> allTypes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        folderId = getIntent().getLongExtra("folder_id", -1);

        spinner = findViewById(R.id.spinner);
        projectNameInput = findViewById(R.id.folder_name_input);
        extra_chips = findViewById(R.id.extra_chips);
        add_project_btn = findViewById(R.id.add_project_btn);
        start_text = findViewById(R.id.start_text);
        end_text = findViewById(R.id.end_text);
        db = DatabaseController.getInstance(this);


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(AddProjectActivity.this, android.R.layout.simple_spinner_item, BoardActivity.states);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
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
                
                // 1. Collect selected extras while on UI thread
                List<Long> selectedTypeIds = new ArrayList<>();
                for (int i = 0; i < extra_chips.getChildCount(); i++) {
                    Chip chip = (Chip) extra_chips.getChildAt(i);
                    if (chip.isChecked() && i < allTypes.size()) {
                        selectedTypeIds.add(allTypes.get(i).getId());
                    }
                }

                // 2. Create and Save Project
                Project pj = new Project();
                pj.setName(inputName);
                pj.setDescription(" ");
                pj.setStart_date(date_st);
                pj.setEnd_date(date_end);
                if (folderId != -1) {
                    pj.setFolder_id(folderId);
                }
                pj.setState(selectedState);
                
                db.addProject(pj, new DatabaseCallback<Long>() {
                    @Override
                    public void onResult(Long newId) {
                        if (selectedTypeIds.isEmpty()) {
                            runOnUiThread(AddProjectActivity.this::finish);
                            return;
                        }

                        // Use a counter to wait for all extras to be saved before finishing
                        java.util.concurrent.atomic.AtomicInteger counter = new java.util.concurrent.atomic.AtomicInteger(selectedTypeIds.size());
                        
                        for (Long typeId : selectedTypeIds) {
                            ProjectExtra extra = new ProjectExtra();
                            extra.setProject_id(newId);
                            extra.setType_id(typeId);
                            extra.setContent("");
                            db.addProjectExtra(extra, resultId -> {
                                if (counter.decrementAndGet() == 0) {
                                    System.out.println("All " + selectedTypeIds.size() + " extras saved, finishing.");
                                    runOnUiThread(AddProjectActivity.this::finish);
                                }
                            });
                        }
                    }});
            }
        });

        loadChips();
    }


    private void showDatePicker(TextView target, AddProjectActivity.OnDatePicked onDatePicked) {
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
        dialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        selectedState = BoardActivity.states[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private interface OnDatePicked {
        void onDatePicked(String date);
    }
    private void loadChips(){
        db.getAllTypes(list -> runOnUiThread(() -> renderTypes(list)));
    }

    private void renderTypes(List<Type> list) {
        this.allTypes = list;
        extra_chips.clearCheck();
        extra_chips.removeAllViews();
        for (Type type : list){
            Chip chip = new Chip(this);
            chip.setText(type.getName());
            chip.setClickable(true);
            chip.setCheckable(true);
            extra_chips.addView(chip);
        }
    }
}