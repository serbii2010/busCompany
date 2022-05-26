package net.thumbtack.school.buscompany.mappers.mybatis;

import net.thumbtack.school.buscompany.model.TripSchedule;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface TripScheduleMapper {
    @Insert("INSERT INTO trip_schedule (trip_id, schedule_id) " +
            "VALUES (#{trip.id}, #{schedule.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(TripSchedule tripSchedule);
}
