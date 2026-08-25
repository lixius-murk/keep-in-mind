package com.example.keep_in_mind.controllers;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.keep_in_mind.models.entities.MindDatabase;
import com.example.keep_in_mind.models.entities.Folder;
import com.example.keep_in_mind.models.entities.Project;
import com.example.keep_in_mind.models.entities.ProjectExtra;
import com.example.keep_in_mind.models.entities.ProjectWithExtras;
import com.example.keep_in_mind.models.entities.Type;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Entry point Activities call into for all database access. Wraps the Room
 * DAOs and runs every call on a background thread (Room forbids DB access
 * on the main thread), delivering the result through a DatabaseCallback.
 *
 * Usage from an Activity:
 *   DatabaseController.getInstance(this).getAllProjects(projects ->
 *       runOnUiThread(() -> updateUi(projects)));
 */
public class DatabaseController {

    private static volatile DatabaseController instance;

    private final MindDatabase db;
    private final ExecutorService executor;

    private DatabaseController(Context context) {
        this.db = MindDatabase.getInstance(context.getApplicationContext());
        this.executor = Executors.newSingleThreadExecutor();
    }

    public static DatabaseController getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (DatabaseController.class) {
                if (instance == null) {
                    instance = new DatabaseController(context);
                }
            }
        }
        return instance;
    }

    public void addProject(Project project, DatabaseCallback<Long> callback) {
        executor.execute(() -> {
            long id = db.projectDao().insert(project);
            if (callback != null) callback.onResult(id);
        });
    }

    public void updateProject(Project project, DatabaseCallback<Void> callback) {
        executor.execute(() -> {
            db.projectDao().update(project);
            if (callback != null) callback.onResult(null);
        });
    }

    public void deleteProject(Project project, DatabaseCallback<Void> callback) {
        executor.execute(() -> {
            db.projectDao().delete(project);
            if (callback != null) callback.onResult(null);
        });
    }

    public void getProjectById(Long id, DatabaseCallback<Project> callback) {
        executor.execute(() -> callback.onResult(db.projectDao().getById(id)));
    }

    public void getAllProjects(DatabaseCallback<List<Project>> callback) {
        executor.execute(() -> callback.onResult(db.projectDao().getAll()));
    }

    public void getProjectsByFolder(Long folderId, DatabaseCallback<List<Project>> callback) {
        executor.execute(() -> callback.onResult(db.projectDao().getByFolder(folderId)));
    }

    public void getProjectWithExtras(Long projectId, DatabaseCallback<ProjectWithExtras> callback) {
        executor.execute(() -> callback.onResult(db.projectDao().getWithExtras(projectId)));
    }

    public void getAllProjectsWithExtras(DatabaseCallback<List<ProjectWithExtras>> callback) {
        executor.execute(() -> callback.onResult(db.projectDao().getAllWithExtras()));
    }

    public void addFolder(Folder folder, DatabaseCallback<Long> callback) {
        executor.execute(() -> {
            long id = db.folderDao().insert(folder);
            if (callback != null) callback.onResult(id);
        });
    }

    public void updateFolder(Folder folder, DatabaseCallback<Void> callback) {
        executor.execute(() -> {
            db.folderDao().update(folder);
            if (callback != null) callback.onResult(null);
        });
    }

    public void deleteFolder(Folder folder, DatabaseCallback<Void> callback) {
        executor.execute(() -> {
            db.folderDao().delete(folder);
            if (callback != null) callback.onResult(null);
        });
    }

    public void getAllFolders(DatabaseCallback<List<Folder>> callback) {
        executor.execute(() -> callback.onResult(db.folderDao().getAll()));
    }

    // ---- ProjectExtra ----

    public void addProjectExtra(ProjectExtra extra, DatabaseCallback<Long> callback) {
        executor.execute(() -> {
            long id = db.projectExtraDao().insert(extra);
            if (callback != null) callback.onResult(id);
        });
    }

    public void updateProjectExtra(ProjectExtra extra, DatabaseCallback<Void> callback) {
        executor.execute(() -> {
            db.projectExtraDao().update(extra);
            if (callback != null) callback.onResult(null);
        });
    }

    public void deleteProjectExtra(ProjectExtra extra, DatabaseCallback<Void> callback) {
        executor.execute(() -> {
            db.projectExtraDao().delete(extra);
            if (callback != null) callback.onResult(null);
        });
    }

    public void getExtrasForProject(Long projectId, DatabaseCallback<List<ProjectExtra>> callback) {
        executor.execute(() -> callback.onResult(db.projectExtraDao().getForProject(projectId)));
    }

    public void getExtrasForProjectAndType(Long projectId, Long typeId,
                                           DatabaseCallback<List<ProjectExtra>> callback) {
        executor.execute(() ->
                callback.onResult(db.projectExtraDao().getForProjectAndType(projectId, typeId)));
    }

    public void addType(Type type, DatabaseCallback<Long> callback) {
        executor.execute(() -> {
            long id = db.typeDao().insert(type);
            if (callback != null) callback.onResult(id);
        });
    }

    public void updateType(Type type, DatabaseCallback<Void> callback) {
        executor.execute(() -> {
            db.typeDao().update(type);
            if (callback != null) callback.onResult(null);
        });
    }

    public void deleteType(Type type, DatabaseCallback<Void> callback) {
        executor.execute(() -> {
            db.typeDao().delete(type);
            if (callback != null) callback.onResult(null);
        });
    }

    public void getAllTypes(DatabaseCallback<List<Type>> callback) {
        executor.execute(() -> callback.onResult(db.typeDao().getAll()));
    }

}