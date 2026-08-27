package com.example.keep_in_mind.activities;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.keep_in_mind.fragments.BuyTabFragment;
import com.example.keep_in_mind.fragments.DoTabFragment;
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
    private FragmentStateAdapter adapter;
    private TabLayoutMediator mediator;


    private final List<ProjectExtra> currentExtras = new ArrayList<>();
    private final List<Fragment> tabFragments = new ArrayList<>();
    private final List<String> tabTitles = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

        projectId = getIntent().getLongExtra("project_id", -1);
        Log.d("ProjectDetails", "Opening project ID: " + projectId);

        project_name = findViewById(R.id.project_name);
        project_start_date = findViewById(R.id.project_start_date);
        project_end_date = findViewById(R.id.project_end_date);
        close_btn = findViewById(R.id.close_btn);
        extras_tabs = findViewById(R.id.extras_tabs);
        extras_pager = findViewById(R.id.extras_pager);

        db = DatabaseController.getInstance(this);
        close_btn.setOnClickListener(v -> finish());

        setupViewPager();

        if (projectId != -1) {
            observeProject();
        }
    }

    private void observeProject() {
        db.getProjectWithExtrasLive(projectId).observe(this, result -> {
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
        });
    }

    private void setupViewPager() {
        adapter = new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return tabFragments.get(position);
            }

            @Override
            public int getItemCount() {
                return tabFragments.size();
            }

            @Override
            public long getItemId(int position) {
                return currentExtras.get(position).getId();
            }

            @Override
            public boolean containsItem(long itemId) {
                for (ProjectExtra extra : currentExtras) {
                    if (extra.getId() != null && extra.getId() == itemId) return true;
                }
                return false;
            }
        };
        extras_pager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void renderProject(ProjectWithExtras data, Map<Long, String> typeNames) {
        if (data.getProject() != null) {
            project_name.setText(data.getProject().getName());
            project_start_date.setText(data.getProject().getStartDate());
            project_end_date.setText(data.getProject().getEndDate());
        }

        Log.d("ProjectDetails", "Rendering project extras count: " + data.getExtras().size());

        currentExtras.clear();
        tabFragments.clear();
        tabTitles.clear();
        for (ProjectExtra extra : data.getExtras()) {
            String type = typeNames.get(extra.getTypeId());
            if (type == null) {
                Log.w("ProjectDetails", "Unknown type ID: " + extra.getTypeId());
                continue;
            }
            
            Log.d("ProjectDetails", "Adding tab for type: " + type);
            currentExtras.add(extra);
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
                    tabFragments.add(DoTabFragment.newInstance(extra.getContent()));
                    tabTitles.add("to-do");
                    break;
                case "to-buy":
                    tabFragments.add(BuyTabFragment.newInstance(extra.getContent()));
                    tabTitles.add("to-buy");
                    break;
                case "ref":
                    tabFragments.add(ImageTabFragment.newInstance(extra.getContent()));
                    tabTitles.add("ref");
                    break;
                default:
                    Log.w("ProjectDetails", "No fragment for type: " + type);
                    currentExtras.remove(currentExtras.size() - 1);
                    break;
            }
        }

        adapter.notifyDataSetChanged();

        if (mediator != null) {
            mediator.detach();
        }
        mediator = new TabLayoutMediator(extras_tabs, extras_pager,
                (tab, position) -> tab.setText(tabTitles.get(position))
        );
        mediator.attach();
    }


}