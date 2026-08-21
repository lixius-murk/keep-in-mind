package com.example.keep_in_mind.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keep_in_mind.R;
import com.example.keep_in_mind.models.entities.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    public interface OnProjectClickListener {
        void onProjectClick(Project project);
    }

    private List<Project> projects = new ArrayList<>();
    private final OnProjectClickListener listener;

    public ProjectAdapter(OnProjectClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<Project> newProjects) {
        this.projects = newProjects != null ? newProjects : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_project, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Project project = projects.get(position);

        holder.projectName.setText(project.getDescription());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onProjectClick(project);
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder {
        final TextView projectName;

        ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.project_name);
        }
    }
}