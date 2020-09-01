package com.example.hw13.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hw13.database.DBTaskSchema;
import com.example.hw13.database.TaskBaseHelper;
import com.example.hw13.database.cursorwrapper.TaskCursorWrapper;
import com.example.hw13.model.State;
import com.example.hw13.model.Task;
import com.example.hw13.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository implements IRepository<Task> {
    //region defind varaible
    SQLiteDatabase mDatabase;
    //endregion
    //region defind static method and variable
    private static List<Task> sTaskList;
    public static TaskRepository sTaskRepository;
    public static Context sContext;
    public static TaskRepository getInstance(Context context) {
        sContext = context;
        if (sTaskRepository == null)
            sTaskRepository = new TaskRepository();
        return sTaskRepository;
    }
    //endregion


    private TaskRepository() {
        sTaskList = new ArrayList<>();
        TaskBaseHelper taskBaseHelper = new TaskBaseHelper(sContext);
        mDatabase = taskBaseHelper.getWritableDatabase();
    }

    @Override
    public void add(Task task) {
        mDatabase.insert(DBTaskSchema.TaskTable.NAME, null, getUserContentValue(task));
    }

    @Override
    public void insertList(List<Task> tasks) {

    }

    @Override
    public void update(Task e) {
        String where = DBTaskSchema.TaskTable.COLS.UUID + "=?";
        String[] whereArgs = {e.getId().toString()};
        mDatabase.update(DBTaskSchema.TaskTable.NAME,getUserContentValue(e),where,whereArgs);
    }

    @Override
    public void delete(Task task) {
        String where = DBTaskSchema.TaskTable.COLS.UUID + "=?";
        String[] whereArgs = {task.getId().toString()};
        mDatabase.delete(DBTaskSchema.TaskTable.NAME, where, whereArgs);
    }

    @Override
    public void delete(UUID uuid) {
        String where = DBTaskSchema.TaskTable.COLS.UUID + "=?";
        String[] whereArgs = {uuid.toString()};
        mDatabase.delete(DBTaskSchema.TaskTable.NAME, where, whereArgs);
    }

    @Override
    public Task get(UUID uuid) {
        String selection = DBTaskSchema.TaskTable.COLS.UUID + "=?";
        String[] selectionArgs = {uuid.toString()};
        TaskCursorWrapper cursorWrapper=queryTasks(selection,selectionArgs);
        return cursorWrapper.getTask(sContext);
    }

    @Override
    public List<Task> getList() {
        List<Task> taskList=new ArrayList<>();
        TaskCursorWrapper cursorWrapper=queryTasks(null,null);
        while (!cursorWrapper.isAfterLast()){
            taskList.add(cursorWrapper.getTask(sContext));
        }
        return taskList;
    }

    public List<Task> getList(State state) {
        List<Task> taskList=new ArrayList<>();
        String selection= DBTaskSchema.TaskTable.COLS.STATE+"=?";
        String[] selectionArgs={state.toString()};
        TaskCursorWrapper cursorWrapper=queryTasks(selection,selectionArgs);
        while (!cursorWrapper.isAfterLast()){
            taskList.add(cursorWrapper.getTask(sContext));
        }
        return taskList;
    }

    public void deletWithState(State state) {
        String where= DBTaskSchema.TaskTable.COLS.STATE+"=?";
        String[] whereArgs={state.toString()};
        mDatabase.delete(DBTaskSchema.TaskTable.NAME,where,whereArgs);
    }

    private ContentValues getUserContentValue(Task task) {
        ContentValues values = new ContentValues();
        values.put(DBTaskSchema.TaskTable.COLS.UUID, task.getId().toString());
        values.put(DBTaskSchema.TaskTable.COLS.TITLE, task.getTitle());
        values.put(DBTaskSchema.TaskTable.COLS.DESCRIBE, task.getDescribe());
        values.put(DBTaskSchema.TaskTable.COLS.STATE, task.getState().toString());
        values.put(DBTaskSchema.TaskTable.COLS.DATE, task.getDate().toString());
        values.put(DBTaskSchema.TaskTable.COLS.USER, task.getUser().getUserId().toString());
        values.put(DBTaskSchema.TaskTable.COLS.TIME, task.getLocalTime().toString());
        return values;
    }

    private TaskCursorWrapper queryTasks(String selection,String[] selectionArgs) {
        Cursor cursor=mDatabase.query(DBTaskSchema.TaskTable.NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        TaskCursorWrapper taskCursorWrapper=new TaskCursorWrapper(cursor);
        return taskCursorWrapper;
    }
}