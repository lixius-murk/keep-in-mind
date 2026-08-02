package com.example.keep_in_mind.models.entities;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.keep_in_mind.models.FolderDao;
import com.example.keep_in_mind.models.ProjectDao;
import com.example.keep_in_mind.models.ProjectExtraDao;
import com.example.keep_in_mind.models.TypeDao;
import com.example.keep_in_mind.models.entities.Folder;
import com.example.keep_in_mind.models.entities.Project;
import com.example.keep_in_mind.models.entities.ProjectExtra;
import com.example.keep_in_mind.models.entities.Type;


// Table schemas are generated automatically from the @Entity classes below,

@Database(
        entities = {Project.class, Folder.class, ProjectExtra.class, Type.class},
        version = 1,
        exportSchema = false
)
public abstract class MindDatabase extends RoomDatabase {

    private static final String DB_NAME = "mind.db";
    private static volatile MindDatabase instance;

    public abstract ProjectDao projectDao();

    public abstract FolderDao folderDao();

    public abstract ProjectExtraDao projectExtraDao();

    public abstract TypeDao typeDao();

    public static MindDatabase getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (MindDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MindDatabase.class,
                            DB_NAME
                    ).build();
                }
            }
        }
        return instance;
    }
}