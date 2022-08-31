package net.thumbtack.school.buscompany.mappers.mybatis.trip;

import net.thumbtack.school.buscompany.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TripMapper {
    @Insert("INSERT INTO `trip` " +
            "(bus_id, from_station_id, to_station_id, start, duration, price) " +
            "VALUES (#{bus.id}, #{fromStation.id}, #{toStation.id}, #{start}, #{duration}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(Trip trip);

    @Select("SELECT * FROM trip WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "bus", javaType = Bus.class, column = "bus_id",
                    one = @One(select = "getBus")),
            @Result(property = "fromStation", javaType = Station.class, column = "from_station_id",
                    one = @One(select = "getStation")),
            @Result(property = "toStation", javaType = Station.class, column = "to_station_id",
                    one = @One(select = "getStation")),
            @Result(property = "schedule", javaType = Schedule.class, column = "id",
                    one = @One(select = "getSchedule")),
            @Result(property = "dates", javaType = List.class, column = "id",
                    many = @Many(select = "getDates"))
    })
    Trip findById(@Param("id") String id);

    @Select("DELETE FROM trip WHERE id = #{id}")
    Integer delete(Trip trip);

    @Select("SELECT * FROM schedule WHERE trip_id = #{id}")
    @Results(value = {
            @Result(property = "fromDate", column = "from_date"),
            @Result(property = "toDate", column = "to_date"),
            @Result(property = "period", column = "period"),
    })
    Schedule getSchedule(String id);

    @Select("SELECT * FROM date_trip WHERE trip_id = #{tripId}")
    List<DateTrip> getDates(String tripId);

    @Select("SELECT * FROM station where id = #{stationId} ")
    Station getStation(String stationId);

    @Select("SELECT * FROM bus where id = #{busId} ")
    @Results(value = {
            @Result(property = "placeCount", column = "place_count")
    })
    Bus getBus(String busId);

    @Update("UPDATE trip SET " +
            "bus_id=#{bus.id}, " +
            "from_station_id=#{fromStation.id}, " +
            "to_station_id=#{toStation.id}, " +
            "start=#{start}, " +
            "duration=#{duration}, " +
            "price=#{price}, " +
            "approved=#{approved} " +
            "WHERE id=#{id}")
    void update(Trip trip);

    @Delete("DELETE FROM trip")
    Integer deleteAll();

    List<Trip> filterTrip(String fromStation, String toStation, String busName, String fromDate, String toDate, Boolean approved);

    @Update("ALTER TABLE trip AUTO_INCREMENT = 1")
    void resetAutoIncrement();
}
