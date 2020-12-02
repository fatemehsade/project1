package com.example.taskmanagerproject.DataBase;

public class TaskDBSchema {
    public static final String NAME="task.db";
    public static final int VERSION=1;

    public static final class Task{
        public static final String NAME="taskTable";

        public static final class TaskColumns{
            public static final String ID="id";
            public static final String USERID="userId";
            public static final String TASKID="taskIs";
            public static final String TITLE="title";
            public static final String DESCRIPTION="description";
            public static final String DATE="date";
            public static final String TIME="time";
            public static final String STATE="state";
        }
    }

    public static final class User{
        public static final String NAME="userTable";

        public static final class UserColumns{
            public static final String ID="id";
            public static final String USERID="userId";
            public static final String USERNAME="userName";
            public static final String Password="password";
        }
    }
}
