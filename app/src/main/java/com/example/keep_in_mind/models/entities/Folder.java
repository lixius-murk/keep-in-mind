package com.example.keep_in_mind.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Folder {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    Long id;


    @ColumnInfo(name = "name")
    String name;
}
