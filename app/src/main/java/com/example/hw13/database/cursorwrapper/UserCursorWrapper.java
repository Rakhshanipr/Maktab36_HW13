package com.example.hw13.database.cursorwrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.hw13.database.DBTaskSchema;
import com.example.hw13.model.State;
import com.example.hw13.model.Task;
import com.example.hw13.model.User;
import com.example.hw13.repository.TaskRepository;
import com.example.hw13.repository.UserRepository;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public User getUser(){


        TaskRepository userRepository=TaskRepository.getInstance();

        UUID uuid = UUID.fromString(getString(getColumnIndex(DBTaskSchema.UserTable.COLS.UUID)));
        String userName = getString(getColumnIndex(DBTaskSchema.UserTable.COLS.USERNAME));
        String password = getString(getColumnIndex(DBTaskSchema.UserTable.COLS.PASSWORD));

        User user=new User(uuid,userName,password);
        return user;



    }
}
