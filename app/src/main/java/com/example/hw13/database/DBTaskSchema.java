package com.example.hw13.database;

public class DBTaskSchema {
    public static final String NAME = "TaskDB.db";
    public static final int VERSION = 1;

    public static final class UserTable {
        public static final String NAME = "UserTable";

        public static final class COLS {
            public static final String ID = "id";
            public static final String UUID = "uuid";
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
        }
    }

    public static final class TaskTable {

        public static final String NAME = "TaskTable";

        public static final class COLS {
            public static final String ID = "id";
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIBE = "describe";
            public static final String DATE = "date";
            public static final String STATE = "state";
            public static final String TIME = "time";
            public static final String USER = "user";
        }
    }
}
