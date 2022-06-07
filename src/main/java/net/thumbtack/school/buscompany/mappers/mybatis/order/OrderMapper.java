package net.thumbtack.school.buscompany.mappers.mybatis.order;

import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.Passenger;
import net.thumbtack.school.buscompany.model.Trip;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrderMapper {
    @Select("SELECT * FROM orders WHERE id=#{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "passengers", javaType = List.class, column = "id",
                    many = @Many(select = "getPassenger")),
            @Result(property = "trip", javaType = Trip.class, column = "trip_id",
                    one = @One(select = "selectTrip"))
    })
    Order findById(String id);

    @Select("SELECT * FROM order_passenger LEFT JOIN passenger ON passenger.id = order_passenger.passenger_id WHERE order_id=#{orderId}")
    List<Passenger> getPassenger(String orderId);

    @Select("SELECT * from trip WHERE id=#{tripId}")
    Trip selectTrip(String tripId);

    @Insert("INSERT INTO orders (trip_id, account_id, date) " +
            "VALUES (#{trip.id}, #{account.id}, #{date})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(Order order);

    List<Order> filter(String fromStation, String toStation, String busName, String fromDate, String toDate, String clientId);
}
