package net.thumbtack.school.buscompany.mappers.mybatis.order;

import net.thumbtack.school.buscompany.model.*;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrderMapper {
    @Select("SELECT * FROM orders WHERE id=#{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "passengers", javaType = List.class, column = "id",
                    many = @Many(select = "getPassenger")),
            @Result(property = "dateTrip", javaType = DateTrip.class, column = "date_trip_id",
                    one = @One(select = "selectDateTrip")),
            @Result(property = "client", javaType = Client.class, column = "client_id",
                    one = @One(select = "selectClient"))
    })
    Order findById(String id);

    @Select("SELECT number FROM place " +
            "WHERE date_trip_id=#{dateTrip.id} AND passenger_id IS NULL")
    List<Integer> findFreePlaces(Order order);

    @Select("SELECT * FROM order_passenger LEFT JOIN passenger ON passenger.id = order_passenger.passenger_id WHERE order_id=#{orderId}")
    List<Passenger> getPassenger(String orderId);

    @Select("SELECT * FROM date_trip WHERE id=#{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "trip", javaType = Trip.class, column = "trip_id",
                    one = @One(select = "selectTrip"))
    })
    DateTrip selectDateTrip(String id);

    @Select("SELECT * FROM trip WHERE id=#{tripId}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "bus", javaType = Bus.class, column = "bus_id",
                    one = @One(select = "selectBus")),
            @Result(property = "fromStation", javaType = Station.class, column = "from_station_id",
                    one = @One(select = "selectStation")),
            @Result(property = "toStation", javaType = Station.class, column = "to_station_id",
                    one = @One(select = "selectStation"))
    })
    Trip selectTrip(String tripId);

    @Select("SELECT * FROM client LEFT JOIN account ON account.id=account_id WHERE client.id=#{id}")
    @Results(value = {
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "patronymic", column = "patronymic"),
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "userType", javaType = UserTypeEnum.class, column = "user_type")
    })
    Client selectClient(String id);

    @Select("SELECT * FROM bus where id = #{busId} ")
    @Results(value = {
            @Result(property = "placeCount", column = "place_count")
    })
    Bus selectBus(String busId);

    @Select("SELECT * FROM station WHERE id=#{id}")
    Station selectStation(String id);

    @Insert("INSERT INTO orders (date_trip_id, client_id) " +
            "VALUES (#{dateTrip.id}, #{client.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(Order order);

    @Select("DELETE FROM orders WHERE id = #{id}")
    Integer delete(Order order);

    @Delete("DELETE FROM orders")
    Integer deleteAll();

    List<Order> filter(String fromStation, String toStation, String busName, String fromDate, String toDate, String clientId);
}
