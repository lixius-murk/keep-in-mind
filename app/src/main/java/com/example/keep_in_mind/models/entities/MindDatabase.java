package com.example.keep_in_mind.models.entities;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.keep_in_mind.models.FolderDao;
import com.example.keep_in_mind.models.ProjectDao;
import com.example.keep_in_mind.models.ProjectExtraDao;
import com.example.keep_in_mind.models.TypeDao;
import com.example.keep_in_mind.models.entities.Folder;
import com.example.keep_in_mind.models.entities.Project;
import com.example.keep_in_mind.models.entities.ProjectExtra;
import com.example.keep_in_mind.models.entities.Type;


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
                            )
                            .addCallback(seedCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    /**
     * Fires exactly once: the first time the database file is created
     * (first app launch, or after app data/db is cleared). Runs the
     * inserts synchronously via raw SQL on the SAME call that opens the
     * database — no separate executor thread — so any query made right
     * after getInstance() is guaranteed to see this seed data. Dispatching
     * this to another thread (as an earlier version did) created a race
     * where getAllTypes() could run before the seed rows were committed.
     */
    private static final RoomDatabase.Callback seedCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            db.execSQL("INSERT INTO type (name) VALUES ('inspiration')");
            db.execSQL("INSERT INTO type (name) VALUES ('note')");
            db.execSQL("INSERT INTO type (name) VALUES ('reference')");
            db.execSQL("INSERT INTO type (name) VALUES ('link')");
        }
    };
}