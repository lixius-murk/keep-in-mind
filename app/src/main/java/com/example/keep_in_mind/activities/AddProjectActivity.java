package com.example.keep_in_mind.activities;



import static com.example.keep_in_mind.R.id.extra_chips;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.keep_in_mind.R;
import com.example.keep_in_mind.controllers.DatabaseController;
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


    private static final String DATE_FORMAT = "%04d-%02d-%02d";
    private ChipGroup extra_chips;
    private Button add_project_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        extra_chips = findViewById(R.id.extra_chips);
        add_project_btn = findViewById(R.id.add_project_btn);

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
            extra_chips.addView(chip);
        }
        System.out.println("added type chip");
    }
}