package com.example.hw13.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.hw13.database.DBTaskSchema.NAME;
import static com.example.hw13.database.DBTaskSchema.TaskTable.COLS;

public class TaskBaseHelper extends SQLiteOpenHelper {
    public TaskBaseHelper(@Nullable Context context) {
        super(context, NAME, null, DBTaskSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE " + DBTaskSchema.TaskTable.NAME + "(" +
                    COLS.ID + " integer primary key autoincrement," +
                    COLS.UUID + " text," +
                    COLS.TITLE + " text," +
                    COLS.DATE + " long," +
                    COLS.STATE + " integer," +
                    COLS.TIME + " text," +
                    COLS.USER + " text," +
                    COLS.DESCRIBE + " text"+
                    ");");;

        sqLiteDatabase.execSQL("CREATE TABLE " + DBTaskSchema.UserTable.NAME + "(" +
                DBTaskSchema.UserTable.COLS.ID + " integer primary key autoincrement," +
                DBTaskSchema.UserTable.COLS.UUID + " text," +
                DBTaskSchema.UserTable.COLS.PASSWORD + " text," +
                DBTaskSchema.UserTable.COLS.USERNAME + " text" +
                ");");;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
