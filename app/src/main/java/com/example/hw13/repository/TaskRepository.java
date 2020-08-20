package com.example.hw13.repository;

import com.example.hw13.model.State;
import com.example.hw13.model.Task;
import com.example.hw13.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository implements IRepository<Task> {

    private static List<Task> sTaskList;
    public static TaskRepository sTaskRepository;

    private TaskRepository() {
        sTaskList = new ArrayList<>();
    }

    public static TaskRepository getInstance() {
        if (sTaskRepository == null)
            sTaskRepository = new TaskRepository();
        return sTaskRepository;
    }

    @Override
    public void add(Task task) {
        sTaskList.add(task);
    }

    @Override
    public void insertList(List<Task> tasks) {
        sTaskList = new ArrayList<>();
        sTaskList = tasks;
    }


    @Override
    public void update(Task e) {
        Task task1 = get(e.getId());
        task1.setTitle(e.getTitle());
        task1.setDescribe(e.getDescribe());
        task1.setState(e.getState());
        task1.setUser(e.getUser());
        task1.setDate(e.getDate());
        task1.setLocalTime(e.getLocalTime());
    }

    @Override
    public void delete(Task task) {
        sTaskList.remove(task);
    }

    @Override
    public void delete(UUID uuid) {
        for (int i = 0; i < sTaskList.size(); i++) {
            if (sTaskList.get(i).getId() == uuid) {
                sTaskList.remove(i);
                return;
            }
        }
    }

    @Override
    public Task get(UUID uuid) {
        for (int i = 0; i < sTaskList.size(); i++) {
            if (sTaskList.get(i).getId() == uuid) {
                return sTaskList.get(i);
            }
        }
        return null;
    }

    @Override
    public List<Task> getList() {
        return sTaskList;
    }

    public List<Task> getList(State state) {
        List<Task> list = new ArrayList<>();
        for (int i = 0; i < sTaskList.size(); i++) {
            if (sTaskList.get(i).getState().equals(state) && sTaskList.get(i).getUser() == User.sOnlineUser) {
                list.add(sTaskList.get(i));
            }
        }
        return list;
    }

    public void deletWithState(State state) {
        boolean reapit=true;
        while (reapit) {
            reapit=false;
            for (int i = 0; i < sTaskList.size(); i++) {
                if (sTaskList.get(i).getState().equals(state) && sTaskList.get(i).getUser() == User.sOnlineUser) {
                    sTaskList.remove(i);
                    reapit=true;
                    break;
                }
            }
        }

    }
}