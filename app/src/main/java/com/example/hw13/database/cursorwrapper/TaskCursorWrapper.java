package com.example.hw13.database.cursorwrapper;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.hw13.database.DBTaskSchema;
import com.example.hw13.model.State;
import com.example.hw13.model.Task;
import com.example.hw13.model.User;
import com.example.hw13.repository.UserRepository;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Task getTask(Context context) {

        UserRepository userRepository=UserRepository.getInstance(context);

        UUID uuid = UUID.fromString(getString(getColumnIndex(DBTaskSchema.TaskTable.COLS.UUID)));
        String title = getString(getColumnIndex(DBTaskSchema.TaskTable.COLS.TITLE));
        String describe = getString(getColumnIndex(DBTaskSchema.TaskTable.COLS.DESCRIBE));
        State state = State.valueOf(getString(getColumnIndex(DBTaskSchema.TaskTable.COLS.STATE)));
        Date date = new Date(getLong(getColumnIndex(DBTaskSchema.TaskTable.COLS.DATE)));
        String localTimeString=getString(getColumnIndex(DBTaskSchema.TaskTable.COLS.TIME));
        LocalTime time =LocalTime.parse(localTimeString);
        User user = userRepository.get(UUID.fromString(getString(getColumnIndex(DBTaskSchema.TaskTable.COLS.USER))));

        Task task=new Task(uuid,title,describe,state,date,time,user);
        return task;
    }
}
