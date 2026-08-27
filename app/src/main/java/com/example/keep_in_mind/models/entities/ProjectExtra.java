package com.example.keep_in_mind.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;


@Entity(
        tableName = "project_extra",
        foreignKeys = {
                @ForeignKey(
                        entity = Project.class,
                        parentColumns = "id",
                        childColumns = "project_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Type.class,
                        parentColumns = "id",
                        childColumns = "type_id",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class ProjectExtra implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private  Long id;

    @ColumnInfo(name = "project_id", index = true)
    private  Long project_id;

    @ColumnInfo(name = "type_id", index = true)
    private  Long type_id;

    @ColumnInfo(name = "content")
    private  String content;

    public ProjectExtra() {
    }


    public ProjectExtra(Long id, Long project_id, Long type_id, String content) {
        this.id = id;
        this.project_id = project_id;
        this.type_id = type_id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public Long getProjectId() {
        return project_id;
    }

    public Long getTypeId() {
        return type_id;
    }

    public String getContent() {
        return content;
    }

    @Ignore
    public ProjectExtra withId(Long newId) {
        return new ProjectExtra(newId, project_id, type_id, content);
    }

    @Override
    public String toString() {
        return "ProjectExtra{" +
                "id=" + id +
                ", project_id=" + project_id +
                ", type_id=" + type_id +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectExtra)) return false;
        ProjectExtra that = (ProjectExtra) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(project_id, that.project_id) &&
                Objects.equals(type_id, that.type_id) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, project_id, type_id, content);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public Long getType_id() {
        return type_id;
    }

    public void setType_id(Long type_id) {
        this.type_id = type_id;
    }

    public void setContent(String content) {
        this.content = content;
    }
}