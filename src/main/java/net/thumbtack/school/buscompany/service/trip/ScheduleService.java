package net.thumbtack.school.buscompany.service.trip;

import net.thumbtack.school.buscompany.daoImpl.trip.ScheduleDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Schedule;
import org.apache.commons.validator.GenericValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class ScheduleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleService.class);

    @Autowired
    private ScheduleDaoImpl scheduleDao;

    public Schedule findOrInsert(Schedule schedule) throws ServerException {
        if (schedule == null) {
            return null;
        }
        Schedule result = scheduleDao.find(schedule);
        if (result == null) {
            result = scheduleDao.insert(schedule);
        }
        return result;
    }

    public boolean checkPeriod(String period) {
        if (period.equalsIgnoreCase("daily")) return true;
        if (period.equalsIgnoreCase("odd")) return true;
        if (period.equalsIgnoreCase("even")) return true;

        List<String> list = Arrays.asList(period.split(","));

        boolean isDayOfWeek = true;
        SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("EEE", Locale.ENGLISH);
        for (String day : list) {
            try {
                formatDayOfWeek.parse(day);
                if (day.length() != 3) {
                    isDayOfWeek = false;
                    break;
                }
            } catch (ParseException e) {
                isDayOfWeek = false;
                break;
            }
        }
        if (isDayOfWeek) return true;

        boolean isDayOfMonth = true;
        for (String day : list) {
            if (!GenericValidator.isDate(day, "dd", false)) {
                isDayOfMonth = false;
                break;
            }
        }

        return isDayOfMonth;
    }
}
