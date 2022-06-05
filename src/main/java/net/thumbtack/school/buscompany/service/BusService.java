package net.thumbtack.school.buscompany.service;

import net.thumbtack.school.buscompany.daoImpl.trip.BusDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Bus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusService.class);

    @Autowired
    private BusDaoImpl busDao;

    public List<Bus> getBuses() {
        return busDao.findAll();
    }

    public Bus findByName(String name) throws ServerException {
        Bus bus = busDao.findByName(name);
        if (bus == null) {
            throw new ServerException(ServerErrorCode.BUS_NOT_FOUND);
        }
        return bus;
    }
}
