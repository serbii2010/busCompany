package net.thumbtack.school.buscompany.mappers.mybatis.order;

import net.thumbtack.school.buscompany.model.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface OrderMapper {
    @Insert("INSERT INTO orders (trip_id, date) " +
            "VALUES (#{trip.id}, #{date})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(Order order);
}
