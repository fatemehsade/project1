package com.example.taskmanagerproject.Repository;

import com.example.taskmanagerproject.Model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository implements Irepository<Task> {
    public static TaskRepository sInstance;
    private List<Task> mTasks=new ArrayList<>();
    private List<Task> mTodoTasks=new ArrayList<>();
    private List<Task> mDoingTasks=new ArrayList<>();
    private List<Task> mDoneTasks=new ArrayList<>();

    public static void setInstance(TaskRepository instance) {
        sInstance = instance;
    }

    public static TaskRepository getInstance() {
        if (sInstance==null){
            sInstance=new TaskRepository();
        }
        return sInstance;
    }


    private TaskRepository() {
    }

    @Override
    public void insert(Task element) {
        mTasks.add(element);

    }

    public void insert(Task element,String taskState){
        switch (taskState){
            case "TODO":
                mTodoTasks.add(element);
                break;
            case "DOING":
                mDoingTasks.add(element);
                break;
            case "DONE":
                mDoneTasks.add(element);
                break;


        }
    }

    @Override
    public void delete(Task element) {
        for (int i = 0; i <mTasks.size() ; i++) {
            if (mTasks.get(i).equals(element)){
                mTasks.remove(i);
            }

        }

    }
    public void delete(Task element,String taskState){
        switch (taskState){
            case "TODO":
                mTodoTasks.remove(element);
                break;
            case "DOING":
                mDoingTasks.remove(element);
                break;
            case "DONE":
                mDoneTasks.remove(element);
                break;

            default:
                return;
        }
    }

    @Override
    public void update(Task element) {

    }

    @Override
    public Task get(List<Task> elemens) {
        return null;
    }


    public List<Task> get(String mTaskState) {
        switch (mTaskState){
            case "TODO":
                return mTodoTasks;
            case "DOING":
                return mDoingTasks;
            case "DONE":
                return mDoneTasks;
            default:
                return null;
        }
    }



    @Override
    public Task getId(UUID element) {
        for (int i = 0; i <mTasks.size() ; i++) {
            if (mTasks.get(i).getTaskId().equals(element))
                return mTasks.get(i);

        }
        return null;
    }

    public boolean setBoolean(boolean flag, String taskState) {
        if (taskState.equals("TODO")) {
            if (mTodoTasks.size() > 0) {
                return flag;
            }
        } else if (taskState.equals("DOING")) {
            if (mDoingTasks.size() > 0) {
                return flag;
            }
        } else if (taskState.equals("DONE")) {

            if (mDoneTasks.size() > 0) {
                return flag;
            }
        }
        return false;
    }


    }
