package net.thumbtack.school.buscompany.mappers.mybatis.trip;

import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.Passenger;
import net.thumbtack.school.buscompany.model.Place;
import org.apache.ibatis.annotations.*;

public interface PlaceMapper {
    @Select("SELECT * FROM place WHERE number=#{number} AND date_trip_id=#{dateTrip.id}")
    @Results(value = {
            @Result(property = "passenger", javaType = Passenger.class, column = "passenger_id",
                    one = @One(select = "selectPassenger")),
            @Result(property = "dateTrip", javaType = DateTrip.class, column = "date_trip_id",
                    one = @One(select = "selectDateTrip"))
    })
    Place find(String number, DateTrip dateTrip);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO place (number, date_trip_id, passenger_id) " +
            "VALUES (#{number}, #{dateTrip.id}, #{passenger.id})")
    Integer insert(Place place);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO place (number, date_trip_id) " +
            "VALUES (#{number}, #{dateTrip.id})")
    Integer insertFree(Place place);

    @Update("UPDATE place SET " +
            "passenger_id=#{passenger.id} " +
            "WHERE number=#{number} AND date_trip_id=#{dateTrip.id}")
    void update(Place place);

    @Update("UPDATE place SET " +
            "passenger_id=NULL " +
            "WHERE date_trip_id=#{dateTrip.id} AND passenger_id=#{passenger.id}")
    void setFreePlace(Place place);

    @Update("UPDATE place SET " +
            "passenger_id=NULL " +
            "WHERE date_trip_id=#{dateTrip.id}")
    void setFreePlaceByOrder(Order order);

    @Delete("DELETE FROM place")
    Integer deleteAll();

    @Select("SELECT * FROM passenger WHERE id=#{id}")
    Passenger selectPassenger(String id);

    @Select("SELECT * FROM date_trip WHERE id=#{id}")
    DateTrip selectDateTrip(String id);
}
