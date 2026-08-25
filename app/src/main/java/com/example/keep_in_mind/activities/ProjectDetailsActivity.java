package com.example.keep_in_mind.activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.keep_in_mind.R;
import com.example.keep_in_mind.controllers.DatabaseCallback;
import com.example.keep_in_mind.controllers.DatabaseController;
import com.example.keep_in_mind.fragments.ImageTabFragment;
import com.example.keep_in_mind.fragments.LinkTabFragment;
import com.example.keep_in_mind.fragments.NoteTabFragment;
import com.example.keep_in_mind.fragments.TabFragment;
import com.example.keep_in_mind.models.entities.ProjectExtra;
import com.example.keep_in_mind.models.entities.ProjectWithExtras;
import com.example.keep_in_mind.models.entities.Type;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProjectDetailsActivity extends AppCompatActivity {


    private TextView project_name;
    private TextView project_start_date;
    private TextView project_end_date;
    private ImageButton close_btn;
    private TabLayout extras_tabs;
    private ViewPager2 extras_pager;
    private DatabaseController db;
    private Long projectId;
    private Long pictureTypeId;
    private ProjectExtra pictureExtra;

    private final List<Fragment> tabFragments = new ArrayList<>();
    private final List<String> tabTitles = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

        projectId = getIntent().getLongExtra("project_id", -1);

        project_name = findViewById(R.id.project_name);
        project_start_date = findViewById(R.id.project_start_date);
        project_end_date = findViewById(R.id.project_end_date);
        close_btn = findViewById(R.id.close_btn);
        extras_tabs = findViewById(R.id.extras_tabs);
        extras_pager = findViewById(R.id.extras_pager);

        db = DatabaseController.getInstance(this);
        close_btn.setOnClickListener(v -> finish());

        if (projectId != -1) {
            loadProject();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (projectId != -1) {
            loadProject();
        }
    }

    private void loadProject() {
        db.getProjectWithExtras(projectId, new DatabaseCallback<ProjectWithExtras>() {
            @Override
            public void onResult(ProjectWithExtras result) {
                if (result == null) return;
                db.getAllTypes(new DatabaseCallback<List<Type>>() {
                    @Override
                    public void onResult(List<Type> types) {
                        Map<Long, String> typeNames = new HashMap<>();
                        for (Type type : types) {
                            typeNames.put(type.getId(), type.getName());
                        }
                        runOnUiThread(() -> renderProject(result, typeNames));
                    }
                });
            }
        });
    }

    private void renderProject(ProjectWithExtras data, Map<Long, String> typeNames) {
        project_name.setText(data.getProject().getDescription());
        project_start_date.setText(data.getProject().getStartDate());
        project_end_date.setText(data.getProject().getEnd_date());

        pictureExtra = null;
        Map<String, ArrayList<String>> contentsByType = new LinkedHashMap<>();
        tabFragments.clear();
        tabTitles.clear();
        for (ProjectExtra extra : data.getExtras()) {
            String type = typeNames.get(extra.getTypeId());
            if (type == null) continue;
            
            switch(type){
                case "note":
                    tabFragments.add(NoteTabFragment.newInstance(extra.getContent()));
                    tabTitles.add("note");
                    break;
                case "moodboard":
                    tabFragments.add(ImageTabFragment.newInstance(extra.getContent()));
                    tabTitles.add("moodboard");
                    break;
                case "link":
                    tabFragments.add(LinkTabFragment.newInstance(extra.getContent()));
                    tabTitles.add("link");
                    break;
                case "to-do":
                    break;
                case "to-buy":
                    break;
                case "ref":
                    tabFragments.add(ImageTabFragment.newInstance(extra.getContent()));
                    tabTitles.add("ref");
                    break;
                default:
                    System.out.println("unknown type: " + type);
                    break;
            }
        }


        extras_pager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return tabFragments.get(position);
            }

            @Override
            public int getItemCount() {
                return tabFragments.size();
            }
        });

        new TabLayoutMediator(extras_tabs, extras_pager,
                (tab, position) -> tab.setText(tabTitles.get(position))
        ).attach();
    }


    private void setExtra(Long typeId, String content) {

    }

}