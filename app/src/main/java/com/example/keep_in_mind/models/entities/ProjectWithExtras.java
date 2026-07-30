package com.example.keep_in_mind.models.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ProjectWithExtras {
    @Embedded
    Project project;

    @Relation(parentColumn = "id", entityColumn = "project_id")
    List<ProjectExtra> extras;
}