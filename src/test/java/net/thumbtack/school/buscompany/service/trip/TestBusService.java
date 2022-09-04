package net.thumbtack.school.buscompany.service.trip;

import net.thumbtack.school.buscompany.daoImpl.trip.BusDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.TripHelper;
import net.thumbtack.school.buscompany.model.Bus;
import net.thumbtack.school.buscompany.service.account.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("dev")
class TestBusService {
    @Autowired
    private TripHelper tripHelper;
    @Autowired
    private AccountHelper accountHelper;

    @InjectMocks
    private BusService busService;

    @Mock
    private BusDaoImpl busDao;
    @Mock
    private AccountService accountService;

    @BeforeEach
    public void init() throws Exception {
        accountHelper.init();
        tripHelper.init();
    }

    @Test
    void findByName() throws Exception {
        Bus bus = tripHelper.getBus();
        Mockito.when(busDao.findByName("Пазик")).thenReturn(bus);
        Bus getBus = busService.findByName("Пазик");
        assertEquals(bus, getBus);
    }

    @Test
    void findByName_notFound() throws Exception {
        Mockito.when(busDao.findByName("Пазик")).thenThrow(new ServerException(ServerErrorCode.BUS_NOT_FOUND));
        assertThrows(ServerException.class, () -> busService.findByName("Пазик"));
    }

    @Test
    void testGetBuses_byClient() throws Exception {
        Mockito.when(accountService.getAuthAccount(accountHelper.getCookie().getValue())).thenReturn(accountHelper.getClient());
        Mockito.doThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN)).when(accountService).checkAdmin(accountHelper.getClient());

        ServerException error = assertThrows(ServerException.class, () -> busService.getBuses(accountHelper.getCookie().getValue()));
        assertEquals(ServerErrorCode.ACTION_FORBIDDEN, error.getErrorCode());
    }
}