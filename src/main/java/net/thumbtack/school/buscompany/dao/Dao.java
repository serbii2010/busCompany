package net.thumbtack.school.buscompany.dao;

import java.util.List;

public interface Dao<T> {
    T findById(String id);
    List<T> findAll();
    T insert(T object);
    void remove(T object);
}
