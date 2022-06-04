package net.thumbtack.school.buscompany.service;

import net.thumbtack.school.buscompany.daoImpl.shop.DateTripDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.shop.ScheduleDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.shop.TripDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class TripService {
    @Autowired
    private TripDaoImpl tripDao;
    @Autowired
    private ScheduleDaoImpl scheduleDao;
    @Autowired
    private DateTripDaoImpl dateTripDao;

    public Trip findById(String id) throws ServerException {
        Trip trip = tripDao.findById(id);
        if (trip == null) {
            throw new ServerException(ServerErrorCode.TRIP_NOT_FOUND);
        }
        return trip;
    }

    public List<Trip> getListTrip(String fromStation, String toStation, String busName, String fromDate, String toDate) {
        return tripDao.filter(fromStation, toStation, busName, fromDate, toDate);
    }


    public Trip insert(Trip trip) {
        if (trip.getDates() == null) {
            try {
                scheduleDao.find(trip.getSchedule());
            } catch (ServerException exception) {
                scheduleDao.insert(trip.getSchedule());
            }

            List<DateTrip> dates = this.generateDates(trip);

            trip.setDates(dates);
        }

        tripDao.insert(trip);

        for (DateTrip date: trip.getDates()) {
            DateTrip dateTrip = new DateTrip(trip, date.getDate());
            dateTripDao.insert(dateTrip);
        }

        return trip;
    }

    public void update(Trip trip) {
        tripDao.update(trip);
        for (DateTrip date: trip.getDates()) {
            DateTrip dateTrip = new DateTrip(trip, date.getDate());
            dateTripDao.insert(dateTrip);
        }
    }

    public List<DateTrip> updateDates(Trip trip) {
        for (DateTrip dateTrip: trip.getDates()) {
            dateTripDao.remove(dateTrip);
        }
        return this.generateDates(trip);
    }

    public void delete(Trip trip) {
        tripDao.remove(trip);
    }

    private List<DateTrip> generateDates(Trip trip) {
        List<DateTrip> dates = new ArrayList<>();

        LocalDate dateStart = trip.getSchedule().getFromDate().toInstant().
                atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateEnd = trip.getSchedule().getToDate().toInstant().
                atZone(ZoneId.systemDefault()).toLocalDate();

        for (LocalDate date = dateStart; date.isBefore(dateEnd.plusDays(1)); date = date.plusDays(1)) {
            if (trip.getSchedule().getPeriod().equals("daily")) {
                dates.add(new DateTrip(trip, Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())));
                continue;
            }
            if (trip.getSchedule().getPeriod().equals("odd")) {
                if (date.getDayOfMonth() % 2 == 1) {
                    dates.add(new DateTrip(trip, Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())));
                    continue;
                }
            }
            if (trip.getSchedule().getPeriod().equals("even")) {
                if (date.getDayOfMonth() % 2 == 0) {
                    dates.add(new DateTrip(trip, Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())));
                    continue;
                }
            }
            if (Arrays.asList(trip.getSchedule().getPeriod().split(","))
                    .contains(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH))) {
                dates.add(new DateTrip(trip, Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())));
                continue;
            }
            if (Arrays.asList(trip.getSchedule().getPeriod().split(",")).contains(String.valueOf(date.getDayOfMonth()))) {
                dates.add(new DateTrip(trip, Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())));
            }
        }
        return dates;
    }
}
