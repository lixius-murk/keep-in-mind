package com.example.keep_in_mind.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

import kotlin.TypeCastException;

//Immutable: to change a value, build a new instance (see withId/withFolderId).

@Entity(
        tableName = "project",
        foreignKeys = @ForeignKey(
                entity = Folder.class,
                parentColumns = "id",
                childColumns = "folder_id",
                onDelete = ForeignKey.CASCADE
        )

)
public class Project implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private  Long id;

    @ColumnInfo(name = "start_date")
    private  String start_date;

    @ColumnInfo(name = "end_date")
    private  String end_date;

    @ColumnInfo(name = "description")
    private  String description;

    @ColumnInfo(name = "state")
    private  String state;

    @ColumnInfo(name = "folder_id", index = true)
    private  Long folder_id;

    public Project() {
        this(null, null, null, null, null, null);
    }

    public Project(Long id, String start_date, String end_date, String description,
                   String state, Long folder_id) {
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
        this.state = state;
        this.folder_id = folder_id;
    }

    public Long getId() {
        return id;
    }

    public String getStartDate() {
        return start_date;
    }

    public String getEndDate() {
        return end_date;
    }

    public String getDescription() {
        return description;
    }

    public String getState() {
        return state;
    }

    public Long getFolderId() {
        return folder_id;
    }

    @Ignore
    public Project withId(Long newId) {
        return new Project(newId, start_date, end_date, description, state, folder_id);
    }

    @Ignore
    public Project withFolderId(Long newFolderId) {
        return new Project(id, start_date, end_date, description, state, newFolderId);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", description=" + description +
                ", state=" + state +
                ", folder_id=" + folder_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) &&
                Objects.equals(start_date, project.start_date) &&
                Objects.equals(end_date, project.end_date) &&
                Objects.equals(description, project.description) &&
                Objects.equals(state, project.state) &&
                Objects.equals(folder_id, project.folder_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start_date, end_date, description, state, folder_id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setFolder_id(Long folder_id) {
        this.folder_id = folder_id;
    }
}