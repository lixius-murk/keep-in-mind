package com.example.keep_in_mind.models.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


//using child table for any extra info about project
@Entity(
        tableName = "project_extra",
        foreignKeys = @ForeignKey(
                entity = Project.class,
                parentColumns = "id",
                childColumns = "project_id",
                onDelete = ForeignKey.CASCADE
        )
)
public class ProjectExtra {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    Long id;

    @ColumnInfo(name = "project_id", index = true)
    Long project_id;

    @ColumnInfo(name = "type")
    String type;
    @ColumnInfo(name = "content")
    String content;
}