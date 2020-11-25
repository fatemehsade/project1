package com.example.taskmanagerproject.Repository;

import com.example.taskmanagerproject.Model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository implements Irepository<User> {
    private List<User> mUsers=new ArrayList<>();

    public List<User> getUsers() {
        return mUsers;
    }

    public void setUsers(List<User> users) {
        mUsers = users;
    }

    public static  UserRepository sInstance ;

    public static UserRepository getInstance() {
        if (sInstance==null)
            sInstance=new UserRepository();
        return sInstance;
    }

    public static void setInstance(UserRepository instance) {
        sInstance = instance;
    }

    private UserRepository() {
    }

    @Override
    public void insert(User element) {
        mUsers.add(element);

    }

    @Override
    public void delete(User element) {
        mUsers.remove(element);

    }

    @Override
    public void update(User element) {

    }

    @Override
    public User get(List<User> elemens) {


        return null;
    }
    public User get(String userName){
        for (int i = 0; i <mUsers.size() ; i++) {
            if (mUsers.get(i).getUserName().equals(userName)){
                return mUsers.get(i);
            }

        }
        return null;
    }


    @Override
    public User getId(UUID element) {
        return null;
    }

    public boolean userExist(String userName) {
            for (User chooseUser : mUsers) {
                if (chooseUser.getUserName().equals(userName))
                    return true;

            }

        return false;
    }

    public boolean userExist(User element){
        for (User userExist:mUsers) {
            if (userExist.equals(element))
                return true;

        }
        return false;
    }

}
