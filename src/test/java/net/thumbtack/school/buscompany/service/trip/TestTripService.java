package net.thumbtack.school.buscompany.service.trip;

import net.thumbtack.school.buscompany.daoImpl.trip.DateTripDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.trip.ScheduleDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.trip.TripDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.helper.DateTripHelper;
import net.thumbtack.school.buscompany.helper.TripHelper;
import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class TestTripService {

    @Autowired
    private TripHelper tripHelper;
    @Autowired
    private DateTripHelper dateTripHelper;
    @Mock
    private TripDaoImpl tripDao;
    @Mock
    private DateTripDaoImpl dateTripDao;
    @Mock
    private ScheduleDaoImpl scheduleDao;
    @InjectMocks
    private TripService tripService;

    @BeforeEach
    public void init() {
        tripHelper.init();
    }

    @Test
    void findById() throws Exception {
        Trip trip = tripHelper.getTrip();
        Mockito.when(tripDao.findById(String.valueOf(trip.getId()))).thenReturn(trip);
        Trip findTrip = tripService.findById(String.valueOf(trip.getId()));
        assertEquals(trip, findTrip);
    }

    @Test
    void findById_notFound() throws Exception {
        Trip trip = tripHelper.getTrip();
        Mockito.when(tripDao.findById(String.valueOf(trip.getId()))).thenReturn(null);
        assertThrows(ServerException.class, () -> tripService.findById(String.valueOf(trip.getId())));
    }

    @Test
    void checkApproved() {
        Trip trip = tripHelper.getTrip();
        trip.setApproved(true);
        assertDoesNotThrow(() -> tripService.checkApproved(trip));
    }

    @Test
    void checkApproved_throws() {
        Trip trip = tripHelper.getTrip();
        trip.setApproved(false);
        assertThrows(ServerException.class, () -> tripService.checkApproved(trip));
    }

    @Test
    void checkNotApproved() {
        Trip trip = tripHelper.getTrip();
        trip.setApproved(true);
        assertThrows(ServerException.class, () -> tripService.checkNotApproved(trip));
    }

    @Test
    void checkNotApproved_throws() {
        Trip trip = tripHelper.getTrip();
        trip.setApproved(true);
        assertThrows(ServerException.class, () -> tripService.checkNotApproved(trip));
    }

    @Test
    void findDateTrip() throws Exception {
        Trip trip = tripHelper.getTrip();
        DateTrip dateTrip = dateTripHelper.getDateTrip(trip);
        Mockito.when(dateTripDao.find(String.valueOf(trip.getId()), dateTrip.getDate().toString())).thenReturn(dateTrip);
        DateTrip findDateTrip = tripService.findDateTrip(String.valueOf(trip.getId()), dateTrip.getDate().toString());
        assertEquals(dateTrip, findDateTrip);
    }

    @Test
    void findDateTrip_notFound() throws Exception {
        Trip trip = tripHelper.getTrip();
        DateTrip dateTrip = dateTripHelper.getDateTrip(trip);
        Mockito.when(dateTripDao.find(String.valueOf(trip.getId()), dateTrip.getDate().toString())).thenReturn(null);
        assertThrows(ServerException.class,
                () -> tripService.findDateTrip(String.valueOf(trip.getId()), dateTrip.getDate().toString()));
    }

    @Test
    void insert() throws Exception {
        Trip trip = tripHelper.getTrip();
        trip.setDates(null);
        Mockito.when(scheduleDao.find(trip.getSchedule())).thenReturn(null);
        Trip insertTrip = tripService.insert(trip);
        assertNotEquals(null, insertTrip.getDates());
    }

}