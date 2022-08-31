package net.thumbtack.school.buscompany.mappers.mybatis.trip;

import net.thumbtack.school.buscompany.model.Schedule;
import net.thumbtack.school.buscompany.model.Trip;
import org.apache.ibatis.annotations.*;

public interface ScheduleMapper {
    @Insert("INSERT INTO schedule (from_date, to_date, period, trip_id) " +
            "VALUES (#{schedule.fromDate}, #{schedule.toDate}, #{schedule.period}, #{schedule.trip.id})")
    @Options(useGeneratedKeys = true, keyProperty = "schedule.id")
    Integer insert(@Param("schedule") Schedule schedule);

    @Select("SELECT * FROM schedule WHERE from_date=#{fromDate} AND to_date=#{toDate} AND period=#{period}")
    @Results(value = {
            @Result(property = "fromDate", column = "from_date"),
            @Result(property = "toDate", column = "to_date"),
            @Result(property = "period", column = "period"),
    })
    Schedule find(Schedule schedule);

    @Select("SELECT * FROM schedule WHERE trip_id=#{id}")
    @Results(value = {
            @Result(property = "fromDate", column = "from_date"),
            @Result(property = "toDate", column = "to_date"),
            @Result(property = "period", column = "period"),
    })
    Schedule findByTrip(Trip trip);

    @Update("UPDATE schedule SET " +
            "from_date = #{fromDate}, " +
            "to_date = #{toDate}, " +
            "period = #{period} " +
            "WHERE id=#{id}")
    Integer update(Schedule schedule);

    @Delete("DELETE FROM schedule")
    Integer deleteAll();

    @Update("ALTER TABLE schedule AUTO_INCREMENT = 1")
    void resetAutoIncrement();
}
