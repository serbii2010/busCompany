package net.thumbtack.school.buscompany.daoImpl.shop;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Trip;

import java.util.List;

public class TripDaoImpl extends DaoImplBase implements Dao<Trip> {
    @Override
    public Trip findById(String id) throws ServerException {
        return null;
    }

    @Override
    public List<Trip> findAll() {
        return null;
    }

    @Override
    public Trip insert(Trip object) {
        return null;
    }

    @Override
    public void remove(Trip object) {

    }

    @Override
    public void update(Trip object) {

    }
}
