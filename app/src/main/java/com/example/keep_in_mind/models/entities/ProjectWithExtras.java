package com.example.keep_in_mind.models.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public class ProjectWithExtras implements Serializable {

    @Embedded
    private final Project project;

    @Relation(parentColumn = "id", entityColumn = "project_id")
    private final List<ProjectExtra> extras;

    public ProjectWithExtras(Project project, List<ProjectExtra> extras) {
        this.project = project;
        this.extras = extras;
    }

    public Project getProject() {
        return project;
    }

    public List<ProjectExtra> getExtras() {
        return extras;
    }

    @Override
    public String toString() {
        return "ProjectWithExtras{project=" + project + ", extras=" + extras + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectWithExtras)) return false;
        ProjectWithExtras that = (ProjectWithExtras) o;
        return Objects.equals(project, that.project) && Objects.equals(extras, that.extras);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, extras);
    }
}