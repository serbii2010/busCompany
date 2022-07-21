package net.thumbtack.school.buscompany.service.order;

import net.thumbtack.school.buscompany.daoImpl.order.PassengerDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.trip.PlaceDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Passenger;
import net.thumbtack.school.buscompany.model.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    @Autowired
    private PlaceDaoImpl placeDao;
    @Autowired
    private PassengerDaoImpl passengerDao;

    public Place choose(Place ticket) {
        placeDao.update(ticket);
        return ticket;
    }

    public Passenger getPassenger(String firstName, String lastName, String passport) throws ServerException {
        Passenger passenger = passengerDao.getPassenger(firstName, lastName, passport);
        if (passenger == null) {
            throw new ServerException(ServerErrorCode.PASSENGER_NOT_FOUND);
        }
        return passenger;
    }

    public Place findPlace(int number, DateTrip dateTrip) throws ServerException {
        Place place = placeDao.find(number, dateTrip);
        if (place == null) {
            throw new ServerException(ServerErrorCode.PLACE_NOT_FOUND);
        }
        if (place.getPassenger() != null) {
            throw new ServerException(ServerErrorCode.PLACE_TAKEN);
        }
        return place;
    }
}
