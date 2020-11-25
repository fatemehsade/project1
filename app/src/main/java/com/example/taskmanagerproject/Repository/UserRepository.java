package com.example.taskmanagerproject.Repository;

import com.example.taskmanagerproject.Model.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository implements Irepository<user> {
    private List<user> mUsers=new ArrayList<>();

    public List<user> getUsers() {
        return mUsers;
    }

    public void setUsers(List<user> users) {
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
    public void insert(user element) {
        mUsers.add(element);

    }

    @Override
    public void delete(user element) {
        mUsers.remove(element);

    }

    @Override
    public void update(user element) {

    }

    @Override
    public user get(List<user> elemens) {


        return null;
    }
    public user get(String userName){
        for (int i = 0; i <mUsers.size() ; i++) {
            if (mUsers.get(i).getUserName().equals(userName)){
                return mUsers.get(i);
            }

        }
        return null;
    }


    @Override
    public user getId(UUID element) {
        return null;
    }

    public boolean userExist(String userName) {
            for (user chooseUser : mUsers) {
                if (chooseUser.getUserName().equals(userName))
                    return true;

            }

        return false;
    }

    public boolean userExist(user element){
        for (user userExist:mUsers) {
            if (userExist.equals(element))
                return true;

        }
        return false;
    }

}
