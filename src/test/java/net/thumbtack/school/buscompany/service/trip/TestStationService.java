package net.thumbtack.school.buscompany.service.trip;

import net.thumbtack.school.buscompany.daoImpl.trip.StationDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.helper.TripHelper;
import net.thumbtack.school.buscompany.model.Station;
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
class TestStationService {

    @Autowired
    private TripHelper tripHelper;
    @Mock
    private StationDaoImpl stationDao;
    @InjectMocks
    private StationService stationService;

    @BeforeEach
    public void init() {
        tripHelper.init();
    }

    @Test
    void findStationByName() throws Exception {
        Station station = tripHelper.getToStation();
        Mockito.when(stationDao.findByName(station.getName())).thenReturn(station);
        Station findStation = stationService.findStationByName(station.getName());
        assertEquals(station, findStation);
    }

    @Test
    void findStationByName_notFound() throws Exception {
        Station station = tripHelper.getToStation();
        Mockito.when(stationDao.findByName(station.getName())).thenReturn(null);
        assertThrows(ServerException.class, () -> stationService.findStationByName(station.getName()));
    }
}