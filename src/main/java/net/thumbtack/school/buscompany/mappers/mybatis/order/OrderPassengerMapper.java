package net.thumbtack.school.buscompany.mappers.mybatis.order;

import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.OrderPassenger;
import net.thumbtack.school.buscompany.model.Passenger;
import net.thumbtack.school.buscompany.model.Trip;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderPassengerMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO order_passenger (order_id, passenger_id) " +
            "VALUES (#{order.id}, #{passenger.id})")
    Integer insert(OrderPassenger object);

    @Select("DELETE FROM order_passenger WHERE id = #{id}")
    Integer delete(OrderPassenger orderPassenger);

    @Select("SELECT * FROM order_passenger WHERE order_id=#{orderId}")
    List<OrderPassenger> findByOrderId(String orderId);
}
