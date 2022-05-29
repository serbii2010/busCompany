package net.thumbtack.school.buscompany.service;

import net.thumbtack.school.buscompany.daoImpl.shop.ScheduleDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Schedule;
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
        for (String day: list) {
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
        SimpleDateFormat formatDayOfMonth = new SimpleDateFormat("dd");
        for (String day: list) {
            try {
                formatDayOfMonth.parse(day);
            } catch (ParseException e) {
                isDayOfMonth = false;
                break;
            }
        }

        return isDayOfMonth;
    }
}
