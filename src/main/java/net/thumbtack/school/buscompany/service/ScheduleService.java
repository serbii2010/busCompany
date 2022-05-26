package net.thumbtack.school.buscompany.service;

import net.thumbtack.school.buscompany.daoImpl.shop.ScheduleDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleService.class);

    @Autowired
    private ScheduleDaoImpl scheduleDao;

    public Schedule findOrInsert(Schedule schedule) throws ServerException {
        Schedule result = scheduleDao.find(schedule);
        if (result == null) {
            result = scheduleDao.insert(schedule);
        }
        return result;
    }
}
