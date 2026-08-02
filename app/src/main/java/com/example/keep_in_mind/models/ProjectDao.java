package com.example.keep_in_mind.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.keep_in_mind.models.entities.Project;
import com.example.keep_in_mind.models.entities.ProjectWithExtras;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    long insert(Project project);

    @Update
    void update(Project project);

    @Delete
    void delete(Project project);

    @Query("SELECT * FROM project WHERE id = :id")
    Project getById(Long id);

    @Query("SELECT * FROM project")
    List<Project> getAll();

    @Query("SELECT * FROM project WHERE folder_id = :folderId")
    List<Project> getByFolder(Long folderId);

    @Transaction
    @Query("SELECT * FROM project WHERE id = :id")
    ProjectWithExtras getWithExtras(Long id);

    @Transaction
    @Query("SELECT * FROM project")
    List<ProjectWithExtras> getAllWithExtras();
}
