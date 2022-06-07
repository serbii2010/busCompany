package net.thumbtack.school.buscompany.mappers.mybatis.order;

import net.thumbtack.school.buscompany.model.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import java.util.List;

public interface OrderMapper {
    @Insert("INSERT INTO orders (trip_id, account_id, date) " +
            "VALUES (#{trip.id}, #{account.id}, #{date})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(Order order);

    List<Order> filter(String fromStation, String toStation, String busName, String fromDate, String toDate, String clientId);
}
