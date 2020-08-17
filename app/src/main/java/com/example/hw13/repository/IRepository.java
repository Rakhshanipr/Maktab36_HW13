package com.example.hw13.repository;

import java.util.List;
import java.util.UUID;

public interface IRepository<E> {
    void add(E e);

    void insertList(List<E> eList);

    void update(E e);

    void delete(E e);

    void delete(UUID uuid);

    E get(UUID uuid);

    List<E> getList();
}
