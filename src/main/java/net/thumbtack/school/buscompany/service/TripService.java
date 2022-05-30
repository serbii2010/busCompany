package net.thumbtack.school.buscompany.service;

import net.thumbtack.school.buscompany.daoImpl.shop.DateTripDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.shop.ScheduleDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.shop.TripDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.shop.TripScheduleDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Schedule;
import net.thumbtack.school.buscompany.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TripService {
    @Autowired
    private TripDaoImpl tripDao;
    @Autowired
    private ScheduleDaoImpl scheduleDao;
    @Autowired
    private DateTripDaoImpl dateTripDao;

    public Trip insert(Trip trip) {
        if (trip.getDates() == null) {
            try {
                scheduleDao.find(trip.getSchedule());
            } catch (ServerException exception) {
                scheduleDao.insert(trip.getSchedule());
            }
            //@todo сформировать даты в рейсе
        }

        tripDao.insert(trip);

        for (DateTrip date: trip.getDates()) {
            DateTrip dateTrip = new DateTrip(trip, date.getDate());
            dateTripDao.insert(dateTrip);
        }

        return trip;
    }
}
