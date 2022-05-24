package net.thumbtack.school.buscompany.mappers.mybatis;

import net.thumbtack.school.buscompany.model.Station;
import net.thumbtack.school.buscompany.model.Trip;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TripMapper {

    @Insert("INSERT INTO `trip` " +
            "(bus_id, from_station_id, to_station_id, start, duration, price) " +
            "VALUES (#{id}, #{fromStation.id}, #{toStation.id}, #{start}, #{duration}, #{price})")
    Integer insetTrip(Trip trip);

    @Select("SELECT * FROM station WHERE id = #{id}")
    Station findStationById(@Param("id") int id);
}
