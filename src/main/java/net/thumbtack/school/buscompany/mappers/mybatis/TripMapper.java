package net.thumbtack.school.buscompany.mappers.mybatis;

import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Schedule;
import net.thumbtack.school.buscompany.model.Trip;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TripMapper {
    @Insert("INSERT INTO `trip` " +
            "(bus_id, from_station_id, to_station_id, schedule_id, start, duration, price) " +
            "VALUES (#{bus.id}, #{fromStation.id}, #{toStation.id}, #{schedule.id}, #{start}, #{duration}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(Trip trip);

    @Select("SELECT * FROM trip WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "schedule", javaType = Schedule.class, column = "schedule_id",
                    one = @One(select = "getSchedule")),
            @Result(property = "dates", javaType = List.class, column = "id",
                    many = @Many(select = "getDates"))
    })
    Trip findById(@Param("id") String id);

    @Select("DELETE FROM trip WHERE id = #{id}")
    Integer delete(Trip trip);

    @Select("SELECT * FROM schedule WHERE id = #{scheduleId}")
    @Results(value = {
            @Result(property = "fromDate", column = "from_date"),
            @Result(property = "toDate", column = "to_date"),
            @Result(property = "period", column = "periods"),
    })
    Schedule getSchedule(String scheduleId);

    @Select("SELECT * FROM date_trip WHERE trip_id = #{tripId}")
    List<DateTrip> getDates(String tripId);
}
