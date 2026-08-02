package com.example.keep_in_mind.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.keep_in_mind.models.entities.ProjectExtra;

import java.util.List;

@Dao
public interface ProjectExtraDao {

    @Insert
    long insert(ProjectExtra extra);

    @Update
    void update(ProjectExtra extra);

    @Delete
    void delete(ProjectExtra extra);

    @Query("SELECT * FROM project_extra WHERE id = :id")
    ProjectExtra getById(Long id);

    @Query("SELECT * FROM project_extra WHERE project_id = :projectId")
    List<ProjectExtra> getForProject(Long projectId);

    @Query("SELECT * FROM project_extra WHERE project_id = :projectId AND type_id = :typeId")
    List<ProjectExtra> getForProjectAndType(Long projectId, Long typeId);
}
