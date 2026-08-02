package com.example.keep_in_mind.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.keep_in_mind.models.entities.Type;

import java.util.List;

@Dao
public interface TypeDao {

    @Insert
    long insert(Type type);

    @Update
    void update(Type type);

    @Delete
    void delete(Type type);

    @Query("SELECT * FROM type WHERE id = :id")
    Type getById(Long id);

    @Query("SELECT * FROM type")
    List<Type> getAll();
}
