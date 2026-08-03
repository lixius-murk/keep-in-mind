package com.example.keep_in_mind.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.keep_in_mind.R;

import java.util.Calendar;
import java.util.Locale;

public class AddFolderActivity extends AppCompatActivity {

    private static final String DATE_FORMAT = "%04d-%02d-%02d";
    private EditText folder_name_input;
    private TextView start_text;
    private TextView end_text;

    private String start_date;
    private String end_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_folder);

        folder_name_input = findViewById(R.id.folder_name_input);
        start_text = findViewById(R.id.start_text);
        end_text = findViewById(R.id.end_text);

        start_text.setOnClickListener(v -> showDatePicker(start_text, picked -> start_date = picked));
        end_text.setOnClickListener(v -> showDatePicker(end_text, picked -> end_date = picked));
    }

    private void showDatePicker(TextView target, OnDatePicked onDatePicked) {
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
}