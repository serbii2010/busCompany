package net.thumbtack.school.buscompany.service;

import net.thumbtack.school.buscompany.daoImpl.shop.ScheduleDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.shop.TripDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.shop.TripScheduleDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Schedule;
import net.thumbtack.school.buscompany.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripService {
    @Autowired
    private TripDaoImpl tripDao;
    @Autowired
    private ScheduleDaoImpl scheduleDao;

    public Trip insert(Trip trip) {
        try {
            Schedule schedule = scheduleDao.find(trip.getSchedule());
        } catch (ServerException exception) {
            scheduleDao.insert(trip.getSchedule());
        }

        tripDao.insert(trip);

        return trip;
    }
}
