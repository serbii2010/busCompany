package net.thumbtack.school.buscompany.service.order;

import net.thumbtack.school.buscompany.daoImpl.order.TicketDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.OrderPassenger;
import net.thumbtack.school.buscompany.model.Passenger;
import net.thumbtack.school.buscompany.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    @Autowired
    private TicketDaoImpl ticketDao;

    public Ticket insert(Ticket ticket) {
        ticketDao.insert(ticket);
        return ticket;
    }

    public Passenger getPassenger(String firstName, String lastName, String passport) throws ServerException {
        Passenger passenger = ticketDao.getPassenger(firstName, lastName, passport);
        if (passenger == null) {
            throw new ServerException(ServerErrorCode.PASSENGER_NOT_FOUND);
        }
        return passenger;
    }

    public OrderPassenger getOrderPassenger(String orderId, Passenger passenger) throws ServerException {
        OrderPassenger orderPassenger = ticketDao.getOrderPassenger(orderId, passenger);
        if (orderPassenger == null) {
            throw new ServerException(ServerErrorCode.PASSENGER_IN_ORDER_NOT_FOUND);
        }
        return orderPassenger;
    }
}
