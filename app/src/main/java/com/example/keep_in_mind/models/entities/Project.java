package com.example.keep_in_mind.models.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "project")

public class Project {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    Long id;

    @ColumnInfo(name = "start_date")
    String start_date;

    @ColumnInfo(name = "end_date")
    String end_date;

    @ColumnInfo(name = "description")
    String description;

    @ColumnInfo(name = "state")
    String state;

    @ForeignKey(Folder.class, parentColumns = "id", childColumns = "folder_id", onDelete = ForeignKey.CASCADE)
    @ColumnInfo(name = "folder_id")
    Long folder_id;


}
