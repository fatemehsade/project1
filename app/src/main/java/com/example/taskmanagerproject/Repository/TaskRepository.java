package com.example.taskmanagerproject.Repository;

import com.example.taskmanagerproject.Model.Task;
import com.example.taskmanagerproject.Model.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository implements Irepository<Task> {
    public static TaskRepository sInstance;
    private List<Task> mTasks = new ArrayList<>();
    private List<Task> mTodoTasks = new ArrayList<>();
    private List<Task> mDoingTasks = new ArrayList<>();
    private List<Task> mDoneTasks = new ArrayList<>();


    public static TaskRepository getInstance() {
        if (sInstance == null) {
            sInstance = new TaskRepository();
        }
        return sInstance;
    }


    private TaskRepository() {
    }

    @Override
    public void insert(Task element) {
        mTasks.add(element);

    }

    public void insert(Task element, String taskState) {
        switch (taskState) {
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
        for (int i = 0; i < mTasks.size(); i++) {
            if (mTasks.get(i).equals(element)) {
                mTasks.remove(i);
            }

        }

    }

    public void delete(Task element, String taskState) {
        switch (taskState) {
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
                break;
        }
    }

    @Override
    public void update(Task element) {

    }

    @Override
    public Task get(List<Task> elemens) {
        return null;
    }


    public List<Task> get(String mTaskState,UUID userId) {
        switch (mTaskState) {
            case "TODO":
                List<Task> newTodoList=new ArrayList<>();
                cutList(userId, newTodoList, mTodoTasks);
                return newTodoList;
                //return mTodoTasks;
            case "DOING":
                List<Task> newDoingList=new ArrayList<>();
                cutList(userId, newDoingList, mDoingTasks);
                return newDoingList;
               // return mDoingTasks;
            case "DONE":
                List<Task> newDoneList=new ArrayList<>();
                cutList(userId, newDoneList, mDoneTasks);
                return newDoneList;
                //return mDoneTasks;
            default:
                return null;
        }
    }

    private void cutList(UUID userId, List<Task> newList, List<Task> ListTasks) {
        for (int i = 0; i < ListTasks.size(); i++) {
            if (ListTasks.get(i).getUserId().equals(userId)) {
                newList.add(ListTasks.get(i));

            }

        }
    }


    @Override
    public Task getId(UUID element) {
        for (int i = 0; i < mTasks.size(); i++) {
            if (mTasks.get(i).getTaskId().equals(element))
                return mTasks.get(i);

        }
        return null;
    }

    public boolean setBoolean(String taskState) {
        switch (taskState) {
            case "TODO":
                return mTodoTasks.size() <= 0;

            case "DOING":
                return mDoingTasks.size() <= 0;
            case "DONE":

                return mDoneTasks.size() <= 0;
        }
        return false;
    }


    public int getPosition(UUID taskId, String taskState) {
        switch (taskState) {
            case "TODO":
                for (int i = 0; i < mTodoTasks.size(); i++) {
                    if (mTodoTasks.get(i).getTaskId().equals(taskId)) {
                        return i;
                    }

                }
                break;
            case "DOING":
                for (int i = 0; i < mDoingTasks.size(); i++) {
                    if (mDoingTasks.get(i).getTaskId().equals(taskId)) {
                        return i;
                    }

                }
                break;
            case "DONE":
                for (int i = 0; i < mDoneTasks.size(); i++) {
                    if (mDoneTasks.get(i).getTaskId().equals(taskId)) {
                        return i;
                    }

                }
                break;
        }
        return 0;

    }

    public void editTask(UUID taskId,String taskState,Task element){
        switch (taskState) {
            case "TODO":
                mTodoTasks.remove(getPosition(taskId, taskState));
                mTodoTasks.add(getPosition(taskId, taskState), element);
                break;
            case "DOING":
                mDoingTasks.remove(getPosition(taskId, taskState));
                mDoingTasks.add(getPosition(taskId, taskState), element);
                break;
            case "DONE":
                mDoneTasks.remove(getPosition(taskId, taskState));
                mDoneTasks.add(getPosition(taskId, taskState), element);
                break;
        }

    }


}
