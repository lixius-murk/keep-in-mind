package com.example.keep_in_mind.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

/**
 * A reusable "type" label for a ProjectExtra (e.g. "inspiration", "note",
 * "reference"), stored as its own table so types can be listed, renamed,
 * or added without touching ProjectExtra rows.
 * Immutable: create a new instance to change values.
 */
@Entity(tableName = "type")
public class Type implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private  Long id;

    @ColumnInfo(name = "name")
    private  String name;

    public Type() {
        this(null, null);
    }

    public Type(Long id, String name) {
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

    @Ignore
    public Type withId(Long newId) {
        return new Type(newId, name);
    }

    @Override
    public String toString() {
        return "Type{id=" + id + ", name='" + name + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Type)) return false;
        Type type = (Type) o;
        return Objects.equals(id, type.id) && Objects.equals(name, type.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}