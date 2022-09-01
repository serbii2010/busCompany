package net.thumbtack.school.buscompany.dao;

import net.thumbtack.school.buscompany.exception.ServerException;

import java.util.List;

public interface Dao<T> {
    T findById(String id) throws ServerException;
    List<T> findAll();
    T insert(T object) throws ServerException;
    void remove(T object) throws ServerException;
    void update(T object) throws ServerException;
}
