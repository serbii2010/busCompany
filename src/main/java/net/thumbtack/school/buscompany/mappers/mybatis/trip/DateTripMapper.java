package net.thumbtack.school.buscompany.mappers.mybatis.trip;

import net.thumbtack.school.buscompany.model.Bus;
import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Station;
import net.thumbtack.school.buscompany.model.Trip;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DateTripMapper {
    @Insert("INSERT INTO date_trip (trip_id, date) " +
            "VALUES (#{trip.id}, #{date})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(DateTrip date);

    @Delete("DELETE FROM date_trip WHERE id=#{id}")
    void delete(DateTrip dateTrip);

    @Delete("DELETE FROM date_trip WHERE trip_id = #{tripId}")
    void deleteByTripId(String tripId);

    @Delete("DELETE FROM date_trip")
    Integer deleteAll();

    @Select("SELECT * FROM date_trip WHERE trip_id=#{tripId} AND date=#{date}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "trip", javaType = Trip.class, column = "trip_id",
                    one = @One(select = "selectTrip")),
            @Result(property = "places", javaType = List.class, column = "id",
                    many = @Many(select = "selectPlaces"))
    })
    DateTrip find(String tripId, String date);

    @Select("SELECT * FROM trip WHERE id=#{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "bus", javaType = Bus.class, column = "bus_id",
                    one = @One(select = "getBus")),
            @Result(property = "fromStation", javaType = Station.class, column = "from_station_id",
                    one = @One(select = "getStation")),
            @Result(property = "toStation", javaType = Station.class, column = "to_station_id",
                    one = @One(select = "getStation")),
    })
    Trip selectTrip(String id);

    @Select("SELECT number FROM place WHERE date_trip_id=#{id}")
    List<Integer> selectPlaces(String id);

    @Select("SELECT * FROM station where id = #{stationId} ")
    Station getStation(String stationId);

    @Select("SELECT * FROM bus where id = #{busId} ")
    @Results(value = {
            @Result(property = "placeCount", column = "place_count")
    })
    Bus getBus(String busId);

    @Update("ALTER TABLE date_trip AUTO_INCREMENT = 1")
    void resetAutoIncrement();
}
