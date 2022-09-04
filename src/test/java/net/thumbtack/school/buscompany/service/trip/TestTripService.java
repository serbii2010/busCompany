package net.thumbtack.school.buscompany.service.trip;

import net.thumbtack.school.buscompany.daoImpl.trip.DateTripDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.trip.ScheduleDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.trip.TripDaoImpl;
import net.thumbtack.school.buscompany.dto.request.trip.TripDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EmptyDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.DateTripHelper;
import net.thumbtack.school.buscompany.helper.TripHelper;
import net.thumbtack.school.buscompany.helper.dto.request.trip.TripDtoRequestHelper;
import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Trip;
import net.thumbtack.school.buscompany.service.account.AccountService;
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
    @Autowired
    private AccountHelper accountHelper;
    @Mock
    private TripDaoImpl tripDao;
    @Mock
    private DateTripDaoImpl dateTripDao;
    @Mock
    private ScheduleDaoImpl scheduleDao;
    @Mock
    private AccountService accountService;
    @InjectMocks
    private TripService tripService;

    @BeforeEach
    public void init() {
        accountHelper.init();
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

    @Test
    void testApproveTrip_tripNotFound() throws Exception {
        Mockito.when(accountService.getAuthAccount(accountHelper.getCookie().getValue())).thenReturn(accountHelper.getAdmin());
        Mockito.when(tripDao.findById("1")).thenReturn(null);

        ServerException error = assertThrows(ServerException.class, () -> tripService.approved(accountHelper.getCookie().getValue(), "1"));
        assertEquals(ServerErrorCode.TRIP_NOT_FOUND, error.getErrorCode());
    }

    @Test
    void testApproveTrip_notAdmin() throws Exception {
        Mockito.doThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN)).when(accountService).checkAdmin(accountHelper.getClient());
        Mockito.when(accountService.getAuthAccount("cookie")).thenReturn(accountHelper.getClient());
        assertThrows(ServerException.class, () -> tripService.approved("cookie", "1"));
    }

    @Test
    void testDeleteTrip() throws Exception {
        Mockito.when(accountService.getAuthAccount(accountHelper.getCookie().getValue())).thenReturn(accountHelper.getClient());
        Mockito.when(tripDao.findById("1")).thenReturn(tripHelper.getTrip());

        EmptyDtoResponse response = new EmptyDtoResponse();
        EmptyDtoResponse getResponse = tripService.delete(accountHelper.getCookie().getValue(), "1");
        assertEquals(response, getResponse);
    }

    @Test
    void testDeleteTrip_notAdmin() throws Exception {
        Mockito.when(accountService.getAuthAccount(accountHelper.getCookie().getValue())).thenReturn(accountHelper.getClient());
        Mockito.doThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN)).when(accountService).checkAdmin(accountHelper.getClient());

        ServerException error = assertThrows(ServerException.class, () -> tripService.delete(accountHelper.getCookie().getValue(), "1"));
        assertEquals(ServerErrorCode.ACTION_FORBIDDEN, error.getErrorCode());
    }

    @Test
    void testDeleteTrip_notFoundTrip() throws Exception {
        Mockito.when(accountService.getAuthAccount(accountHelper.getCookie().getValue())).thenReturn(accountHelper.getAdmin());

        Mockito.when(tripDao.findById("1")).thenReturn(null);

        ServerException error = assertThrows(ServerException.class, () -> tripService.delete(accountHelper.getCookie().getValue(), "1"));
        assertEquals(ServerErrorCode.TRIP_NOT_FOUND, error.getErrorCode());
    }

    @Test
    void testDeleteTrip_approvedTrip() throws Exception {
        Mockito.when(accountService.getAuthAccount(accountHelper.getCookie().getValue())).thenReturn(accountHelper.getAdmin());
        Mockito.when(tripDao.findById("1")).thenReturn(tripHelper.getTripApproved());

        ServerException error = assertThrows(ServerException.class, () -> tripService.delete(accountHelper.getCookie().getValue(), "1"));
        assertEquals(ServerErrorCode.ACTION_FORBIDDEN, error.getErrorCode());
    }

    @Test
    void testGetTrip_notAdmin() throws Exception {
        Mockito.when(accountService.getAuthAccount(accountHelper.getCookie().getValue())).thenReturn(accountHelper.getClient());
        Mockito.doThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN)).when(accountService).checkAdmin(accountHelper.getClient());

        ServerException error = assertThrows(ServerException.class, () -> tripService.getTrip(accountHelper.getCookie().getValue(), "1"));
        assertEquals(ServerErrorCode.ACTION_FORBIDDEN, error.getErrorCode());
    }

    @Test
    void testGetTrip_notFoundTrip() throws Exception {
        Mockito.when(accountService.getAuthAccount(accountHelper.getCookie().getValue())).thenReturn(accountHelper.getAdmin());
        Mockito.when(tripDao.findById("1")).thenReturn(null);

        ServerException error = assertThrows(ServerException.class, () -> tripService.getTrip(accountHelper.getCookie().getValue(), "1"));
        assertEquals(ServerErrorCode.TRIP_NOT_FOUND, error.getErrorCode());
    }

    @Test
    void testUpdate_notAdmin() throws Exception {
        Mockito.when(accountService.getAuthAccount(accountHelper.getCookie().getValue())).thenReturn(accountHelper.getClient());
        Mockito.doThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN)).when(accountService).checkAdmin(accountHelper.getClient());

        TripDtoRequest request = TripDtoRequestHelper.getUpdateToScheduleEven();
        ServerException error = assertThrows(ServerException.class, () -> tripService.update(accountHelper.getCookie().getValue(), "1", request));
        assertEquals(ServerErrorCode.ACTION_FORBIDDEN, error.getErrorCode());
    }

    @Test
    void testAddTrip_scheduleNotAdmin() throws Exception {
        Mockito.when(accountService.getAuthAccount(accountHelper.getCookie().getValue())).thenReturn(accountHelper.getClient());
        Mockito.doThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN)).when(accountService).checkAdmin(accountHelper.getClient());

        TripDtoRequest request = TripDtoRequestHelper.getWithScheduleEven();
        ServerException error = assertThrows(ServerException.class, () -> tripService.addTrip(accountHelper.getCookie().getValue(), request));
        assertEquals(ServerErrorCode.ACTION_FORBIDDEN, error.getErrorCode());
    }
}