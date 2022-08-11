package net.thumbtack.school.buscompany.service.order;

import net.thumbtack.school.buscompany.daoImpl.order.PassengerDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.trip.PlaceDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.helper.DateTripHelper;
import net.thumbtack.school.buscompany.helper.TicketHelper;
import net.thumbtack.school.buscompany.helper.TripHelper;
import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Passenger;
import net.thumbtack.school.buscompany.model.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestTicketService {

    @Autowired
    private TicketHelper ticketHelper;
    @Autowired
    private TripHelper tripHelper;
    @Autowired
    private DateTripHelper dateTripHelper;

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private PassengerDaoImpl passengerDao;
    @Mock
    private PlaceDaoImpl placeDao;

    @BeforeEach
    public void init() throws Exception {
        ticketHelper.init();
    }

    @Test
    void getPassenger() throws Exception {
        Passenger passenger = ticketHelper.getPassenger();
        Mockito.when(passengerDao.getPassenger(
                passenger.getFirstName(), passenger.getLastName(), passenger.getPassport()
                )
        ).thenReturn(passenger);

        Passenger getPassenger = ticketService.getPassenger(
                passenger.getFirstName(), passenger.getLastName(), passenger.getPassport()
        );

        assertEquals(passenger, getPassenger);
    }

    @Test
    void getPassenger_notFound() throws Exception {
        Passenger passenger = ticketHelper.getPassenger();
        Mockito.when(passengerDao.getPassenger(
                passenger.getFirstName(), passenger.getLastName(), passenger.getPassport()
                )
        ).thenReturn(null);

        assertThrows(ServerException.class, () -> ticketService.getPassenger(
                passenger.getFirstName(), passenger.getLastName(), passenger.getPassport()
        ));
    }


    @Test
    void findPlace() throws Exception {
        Place place = ticketHelper.getFreePlace();
        DateTrip dateTrip = dateTripHelper.getDateTrip(tripHelper.getTrip());
        Mockito.when(placeDao.find(1, dateTrip)).thenReturn(place);

        assertEquals(place, ticketService.findPlace(1, dateTrip));
    }


    @Test
    void findPlace_notFound() throws Exception {
        Place place = ticketHelper.getFreePlace();
        DateTrip dateTrip = dateTripHelper.getDateTrip(tripHelper.getTrip());
        Mockito.when(placeDao.find(1, dateTrip)).thenReturn(null);

        assertThrows(ServerException.class, () -> ticketService.findPlace(1, dateTrip));
    }

    @Test
    void findPlace_taken() throws Exception {
        Place place = ticketHelper.getPlace();
        DateTrip dateTrip = dateTripHelper.getDateTrip(tripHelper.getTrip());
        Mockito.when(placeDao.find(1, dateTrip)).thenReturn(place);

        assertThrows(ServerException.class, () -> ticketService.findPlace(1, dateTrip));
    }
}