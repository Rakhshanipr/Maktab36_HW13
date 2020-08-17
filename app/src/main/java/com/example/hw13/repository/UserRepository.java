package com.example.hw13.repository;

import com.example.hw13.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository implements IRepository<User> {
    private static List<User> mListUsers;
    private static UserRepository mUserRepository;

    private UserRepository() {
        mListUsers=new ArrayList<>();
    }

    public static UserRepository getInstance() {
        if (mUserRepository == null)
            mUserRepository = new UserRepository();
        return mUserRepository;
    }

    @Override
    public void add(User user) {
        mListUsers.add(user);
    }

    @Override
    public void insertList(List<User> users) {
        mListUsers = users;
    }

    @Override
    public void update(User user) {
        User oldUser=get(user.getUserId());
        oldUser.setUserName(user.getUserName());
        oldUser.setPassword(user.getPassword());
    }

    @Override
    public void delete(User user) {
        for (int i = 0; i < mListUsers.size(); i++) {
            if (mListUsers.get(i).getUserId() == user.getUserId())
                mListUsers.remove(i);
        }
    }

    @Override
    public void delete(UUID uuid) {
        for (int i = 0; i < mListUsers.size(); i++) {
            if (mListUsers.get(i).getUserId() == uuid)
                mListUsers.remove(i);
        }
    }

    @Override
    public User get(UUID uuid) {
        for (int i = 0; i < mListUsers.size(); i++) {
            if (mListUsers.get(i).getUserId() == uuid)
               return mListUsers.get(i);
        }

        return null;
    }

    @Override
    public List<User> getList() {
        return mListUsers;
    }


    public boolean isValid(String username,String password){
        for (int i=0;i<mListUsers.size();i++){
            if (mListUsers.get(i).getUserName().equals(username)&&mListUsers.get(i).getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
}
