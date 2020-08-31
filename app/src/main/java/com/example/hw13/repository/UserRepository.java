package com.example.hw13.repository;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hw13.database.DBTaskSchema;
import com.example.hw13.database.TaskBaseHelper;
import com.example.hw13.database.cursorwrapper.UserCursorWrapper;
import com.example.hw13.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository implements IRepository<User> {
    //region defind variable
    SQLiteDatabase mDatabase;
    static Context mContext;
    //endregion
    //region defind static method and variable
    private static List<User> mListUsers;
    private static UserRepository mUserRepository;
    public static UserRepository getInstance(Context context) {
        mContext=context.getApplicationContext();
        if (mUserRepository == null)
            mUserRepository = new UserRepository();
        return mUserRepository;
    }
    //endregion


    private UserRepository() {
        mListUsers=new ArrayList<>();
        TaskBaseHelper taskBaseHelper=new TaskBaseHelper(mContext);
        mDatabase= taskBaseHelper.getWritableDatabase();
    }

    @Override
    public void add(User user) {
        mDatabase.insert(DBTaskSchema.UserTable.NAME,null,getUserContentValue(user));
    }

    @Override
    public void insertList(List<User> users) {
//        mListUsers = users;
    }

    @Override
    public void update(User user) {
        ContentValues values = getUserContentValue(user);
        String where = DBTaskSchema.UserTable.COLS.UUID + "=?";
        String[] whereArgs = new String[]{user.getUserId().toString()};
        mDatabase.update(DBTaskSchema.UserTable.NAME, values, where, whereArgs);
    }

    @Override
    public void delete(User user) {
        String where= DBTaskSchema.UserTable.COLS.UUID+"=?";
        String[] whereArgs=new String[]{user.getUserId().toString()};
        mDatabase.delete(DBTaskSchema.UserTable.NAME,where,whereArgs);
    }

    @Override
    public void delete(UUID uuid) {
        String where= DBTaskSchema.UserTable.COLS.UUID+"=?";
        String[] whereArgs=new String[]{uuid.toString()};
        mDatabase.delete(DBTaskSchema.UserTable.NAME,where,whereArgs);
    }

    @Override
    public User get(UUID uuid) {
        String selection = DBTaskSchema.UserTable.COLS.UUID + "=?";
        String[] selectionArgs = new String[]{uuid.toString()};

        UserCursorWrapper cursor = queryUsers(selection, selectionArgs);
        try {
            cursor.moveToFirst();
            return cursor.getUser();
        } finally {
            cursor.close();
        }
    }

    @Override
    public List<User> getList() {
        List<User> users = new ArrayList<>();
        UserCursorWrapper cursor = queryUsers(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                users.add(cursor.getUser());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return users;
    }

    public boolean isValid(String username,String password){
        String selection = DBTaskSchema.UserTable.COLS.USERNAME + "=? and "+DBTaskSchema.UserTable.COLS.PASSWORD+"=?";
        String[] selectionArgs = new String[]{username,password};

        UserCursorWrapper cursor = queryUsers(selection, selectionArgs);
        try {
            if (cursor.getCount()>0){
                return true;
            }else{
                return false;
            }
        } finally {
            cursor.close();
        }
    }

    private ContentValues getUserContentValue(User user){
        ContentValues values = new ContentValues();
        values.put(DBTaskSchema.UserTable.COLS.UUID, user.getUserId().toString());
        values.put(DBTaskSchema.UserTable.COLS.USERNAME, user.getUserName());
        values.put(DBTaskSchema.UserTable.COLS.PASSWORD, user.getPassword());
        return values;
    }

    private UserCursorWrapper queryUsers(String selection, String[] selectionArgs) {
        Cursor cursor = mDatabase.query(DBTaskSchema.UserTable.NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);

        UserCursorWrapper userCursorWrapper = new UserCursorWrapper(cursor);
        return userCursorWrapper;
    }

}
