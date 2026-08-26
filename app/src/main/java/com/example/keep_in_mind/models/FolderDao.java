package com.example.keep_in_mind.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.keep_in_mind.models.entities.Folder;

import java.util.List;

@Dao
public interface FolderDao {

    @Insert
    long insert(Folder folder);

    @Update
    void update(Folder folder);

    @Delete
    void delete(Folder folder);

    @Query("SELECT * FROM folder WHERE id = :id")
    Folder getById(Long id);

    @Query("SELECT * FROM folder")
    List<Folder> getAll();

    @Query("SELECT * FROM folder")
    LiveData<List<Folder>> getAllLive();
}
