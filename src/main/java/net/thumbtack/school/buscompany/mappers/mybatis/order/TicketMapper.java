package net.thumbtack.school.buscompany.mappers.mybatis.order;

import net.thumbtack.school.buscompany.model.*;
import org.apache.ibatis.annotations.*;

public interface TicketMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO ticket (order_passenger_id, place) " +
            "VALUES (#{orderPassenger.id}, #{place})")
    Integer insert(Ticket ticket);

    @Select("SELECT * FROM ticket WHERE id=#{id}")
    Ticket findById(String id);

    @Select("SELECT * FROM ticket WHERE order_passenger_id=#{id}")
    Ticket findByOrderPassengerId(String id);

    @Select("SELECT * FROM passenger WHERE first_name LIKE #{firstName} AND last_name LIKE #{lastName} AND passport LIKE #{passport}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "passport", column = "passport")
    })
    Passenger findPassenger(String firstName, String lastName, String passport);

    @Select("SELECT * FROM order_passenger " +
            "JOIN passenger ON order_passenger.passenger_id=passenger.id " +
            "WHERE order_passenger.order_id=#{orderId} AND order_passenger.passenger_id=#{passenger.id}")
    @Results(value = {
            @Result(property = "order", javaType = Order.class, column = "order_id",
                    one = @One(select = "selectOrder")),
            @Result(property = "passenger", javaType = Passenger.class, column = "passenger_id",
                    one = @One(select = "selectPassenger"))
    })
    OrderPassenger selectOrderPassenger(String orderId, Passenger passenger);

    @Select("SELECT * FROM orders WHERE id=#{id}")
    @Results(value = {
            @Result(property = "trip", javaType = Trip.class, column = "trip_id",
                    one = @One(select = "selectTrip"))
    })
    Order selectOrder(String id);

    @Select("SELECT * FROM passenger WHERE id=#{id}")
    @Results(value = {
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "passport", column = "passport")
    })
    Passenger selectPassenger(String id);

    @Select("SELECT * FROM trip WHERE id=#{id}")
    Trip selectTrip(String id);

    @Select("DELETE FROM ticket WHERE id = #{id}")
    Integer delete(Ticket ticket);

    @Delete("DELETE FROM ticket")
    Integer deleteAll();
}
