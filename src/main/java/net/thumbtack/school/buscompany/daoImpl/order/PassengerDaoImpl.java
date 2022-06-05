package net.thumbtack.school.buscompany.daoImpl.order;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Passenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PassengerDaoImpl extends DaoImplBase implements Dao<Passenger> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PassengerDaoImpl.class);

    @Override
    public Passenger findById(String id) throws ServerException {
        return null;
    }

    @Override
    public List<Passenger> findAll() {
        return null;
    }

    @Override
    public Passenger insert(Passenger object) {
        return null;
    }

    @Override
    public void remove(Passenger object) {

    }

    @Override
    public void update(Passenger object) {

    }
}
