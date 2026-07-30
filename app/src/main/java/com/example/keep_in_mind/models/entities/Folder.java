package com.example.keep_in_mind.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;



@Entity(tableName = "folder")
public class Folder implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private final Long id;

    @ColumnInfo(name = "name")
    private final String name;

    public Folder() {
        this(null, null);
    }


    public Folder(Long id, String name) {
        if (name != null && name.trim().isEmpty()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Folder withId(Long newId) {
        return new Folder(newId, name);
    }

    @Override
    public String toString() {
        return "Folder{id=" + id + ", name=" + name + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Folder)) return false;
        Folder folder = (Folder) o;
        return Objects.equals(id, folder.id) && Objects.equals(name, folder.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}