package net.thumbtack.school.buscompany.mappers.mybatis.order;

import net.thumbtack.school.buscompany.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrderMapper {
    @Select("SELECT * FROM orders WHERE id=#{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "passengers", javaType = List.class, column = "id",
                    many = @Many(select = "getPassenger")),
            @Result(property = "trip", javaType = Trip.class, column = "trip_id",
                    one = @One(select = "selectTrip")),
            @Result(property = "account", javaType = Account.class, column = "account_id",
                    one = @One(select = "selectAccount"))
    })
    Order findById(String id);

    @Select("SELECT place FROM order_passenger " +
            "RIGHT JOIN ticket ON ticket.order_passenger_id=order_passenger.id " +
            "LEFT JOIN orders ON order_passenger.order_id=orders.id " +
            "WHERE orders.trip_id=#{trip.id} AND orders.date=#{date}")
    List<Integer> findPlaces(Order order);

    @Select("SELECT * FROM order_passenger LEFT JOIN passenger ON passenger.id = order_passenger.passenger_id WHERE order_id=#{orderId}")
    List<Passenger> getPassenger(String orderId);

    @Select("SELECT * from trip WHERE id=#{tripId}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "bus", javaType = Bus.class, column = "bus_id",
                    one = @One(select = "getBus")),
    })
    Trip selectTrip(String tripId);

    @Select("SELECT * FROM bus where id = #{busId} ")
    @Results(value = {
            @Result(property = "placeCount", column = "place_count")
    })
    Bus getBus(String busId);

    @Insert("INSERT INTO orders (trip_id, account_id, date) " +
            "VALUES (#{trip.id}, #{account.id}, #{date})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(Order order);

    @Select("DELETE FROM orders WHERE id = #{id}")
    Integer delete(Order order);

    List<Order> filter(String fromStation, String toStation, String busName, String fromDate, String toDate, String clientId);
}
