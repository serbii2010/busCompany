package net.thumbtack.school.dao;

import java.util.List;

public interface Dao<T> {
    T findById(String id);
    List<T> findAll();
    void insert(T object);
    void remove(T object);
}
