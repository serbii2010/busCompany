package net.thumbtack.school.buscompany.service.trip;

import net.thumbtack.school.buscompany.daoImpl.trip.ScheduleDaoImpl;
import net.thumbtack.school.buscompany.helper.TripHelper;
import net.thumbtack.school.buscompany.model.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("dev")
class TestScheduleService {

    @Autowired
    private TripHelper tripHelper;

    @Mock
    private ScheduleDaoImpl scheduleDao;

    @InjectMocks
    private ScheduleService scheduleService;

    @BeforeEach
    public void init() {
        tripHelper.init();
    }

    @Test
    void findOrInsert() throws Exception {
        Schedule schedule = tripHelper.getSchedule();
        Mockito.when(scheduleDao.find(schedule)).thenReturn(schedule);
        Schedule findSchedule = scheduleService.findOrInsert(schedule);
        assertEquals(schedule, findSchedule);
    }

    @Test
    void findOrInsert_insert() throws Exception {
        Schedule schedule = tripHelper.getSchedule();
        Mockito.when(scheduleDao.find(schedule)).thenReturn(null);
        Mockito.when(scheduleDao.insert(schedule)).thenReturn(schedule);
        Schedule findSchedule = scheduleService.findOrInsert(schedule);
        assertEquals(schedule, findSchedule);
    }

    @Test
    void checkPeriod_daily() {
        String period = "daily";
        boolean result = scheduleService.checkPeriod(period);
        assertTrue(result);
    }

    @Test
    void checkPeriod_odd() {
        String period = "odd";
        boolean result = scheduleService.checkPeriod(period);
        assertTrue(result);
    }

    @Test
    void checkPeriod_even() {
        String period = "even";
        boolean result = scheduleService.checkPeriod(period);
        assertTrue(result);
    }

    @Test
    void checkPeriod_dayOfWeek() {
        String period = "Fri,Sat";
        boolean result = scheduleService.checkPeriod(period);
        assertTrue(result);
    }

    @Test
    void checkPeriod_dayOfMonth() {
        String period = "1,13,25,31";
        boolean result = scheduleService.checkPeriod(period);
        assertTrue(result);
    }
}