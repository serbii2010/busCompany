package net.thumbtack.school.buscompany.service.order;

import net.thumbtack.school.buscompany.daoImpl.order.PassengerDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.trip.PlaceDaoImpl;
import net.thumbtack.school.buscompany.dto.request.order.TicketDtoRequest;
import net.thumbtack.school.buscompany.dto.response.order.TicketDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.mappers.dto.order.TicketMapper;
import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.Passenger;
import net.thumbtack.school.buscompany.model.Place;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TicketService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PlaceDaoImpl placeDao;
    @Autowired
    private PassengerDaoImpl passengerDao;

    public List<Integer> getFreePlace(String javaSessionId, String id) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        Client client = accountService.findClient(account);
        Order order = orderService.findById(id);
        orderService.checkAccount(order, client);
        return orderService.getFreePlaces(order);
    }

    public TicketDtoResponse insertTicket(String javaSessionId, TicketDtoRequest request) throws ServerException {
        Passenger passenger = getPassenger(request.getFirstName(), request.getLastName(), request.getPassport());
        Order order = orderService.findById(request.getOrderId());
        if (order == null) {
            throw new ServerException(ServerErrorCode.ORDER_NOT_FOUND);
        }
        Account account = accountService.getAuthAccount(javaSessionId);
        Client client = accountService.findClient(account);
        if (!order.getClient().equals(client)) {
            throw new ServerException(ServerErrorCode.ACTION_FORBIDDEN);
        }
        Place ticket = findPlace(Integer.parseInt(request.getPlace()), order.getDateTrip());
        ticket.setPassenger(passenger);

        placeDao.update(ticket);
        return TicketMapper.INSTANCE.tickerToDto(ticket, order, passenger);
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
