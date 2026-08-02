package com.example.keep_in_mind.controllers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.keep_in_mind.models.entities.Project;
import com.example.keep_in_mind.models.entities.ProjectExtra;

import java.util.ArrayList;

public class DatabaseController extends SQLiteOpenHelper {


    private static final String DB_NAME = "mind.db";
    private static final int DB_VERSION = 1;

    public DatabaseController(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE stops(id integer PRIMARY KEY AUTOINCREMENT, name TEXT, lat DOUBLE, longt DOUBLE);");
        db.execSQL("CREATE TABLE plans(id integer PRIMARY KEY AUTOINCREMENT, name TEXT, data TEXT, state TEXT);");
        db.execSQL("CREATE TABLE ps(id integer PRIMARY KEY AUTOINCREMENT, id_plan integer, id_stop integer,  FOREIGN KEY(id_stop) REFERENCES stops(id), FOREIGN KEY(id_plan) REFERENCES plans(id));");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS stops;");
        db.execSQL("DROP TABLE IF EXISTS plans;");
        db.execSQL("DROP TABLE IF EXISTS ps;");
        onCreate(db);
    }

    public ArrayList<Project> getAllProjects() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM project;", null);
        ArrayList<Project> list = new ArrayList<>();

        while (cur.moveToNext()) {
            Project pj = new Project();
            pj.setId(cur.getLong(0));
            pj.setStart_date(cur.getString(1));
            pj.setEnd_date(cur.getString(2));
            pj.setDescription(cur.getString(3));
            pj.setState(cur.getString(4));
            pj.setFolder_id(cur.getLong(5));

            list.add(pj);
        }
        cur.close();
        return list;
    }

    public ArrayList<ProjectExtra> getExtrsForProj(Long id) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<ProjectExtra> list = new ArrayList<>();
        Cursor cur = db.rawQuery("SELECT * FROM project_extra WHERE project_id=?", id);
        while (cur.moveToNext()) {
            ProjectExtra pj = new ProjectExtra();
            pj.setId(cur.getLong(0));
            pj.setProject_id(cur.getLong(1));
            pj.setType(cur.getLong(2));
            pj.setContent(cur.getString(3));
            list.add(pj);
        }
        cur.close();
        return list;

    }

}
