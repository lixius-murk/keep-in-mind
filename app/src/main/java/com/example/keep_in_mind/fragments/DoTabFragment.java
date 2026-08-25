package com.example.keep_in_mind.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.keep_in_mind.R;
public class DoTabFragment extends TabFragment {
    private static final String ARG_TASK_LOCATION = "task_location";
    private static final String ITEM_DELIMITER = "|";
    private String task_location;
    public DoTabFragment() {
        // Required empty public constructor
    }
    public static BuyTabFragment newInstance(String content) {
        BuyTabFragment fragment = new BuyTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TASK_LOCATION, content);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            task_location = getArguments().getString(ARG_TASK_LOCATION);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_task_tab, container, false);
        LinearLayout tasksContainer = root.findViewById(R.id.tasks_container);
        if (task_location != null && !task_location.isEmpty()) {
            for (String line : task_location.split("\n")) {
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\" + ITEM_DELIMITER, 2);
                String name = parts[0];
                String price = parts.length > 1 ? parts[1] : "";

                View itemView = inflater.inflate(R.layout.item_do, tasksContainer, false);
                TextView nameText = itemView.findViewById(R.id.name_text);
                CheckBox doneBox = itemView.findViewById(R.id.done_box);

                nameText.setText(name);

                nameText.setOnClickListener(v -> {
                    // TODO: edit text
                });

                doneBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    // TODO: persist done state, e.g. strike-through nameText
                });

                tasksContainer.addView(itemView);
            }
        }
        return root;
    }
    @Override
    public String getType(){
        return "to-do";
    }
}