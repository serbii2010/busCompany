package net.thumbtack.school.buscompany.mappers.mybatis.order;

import net.thumbtack.school.buscompany.model.Passenger;
import org.apache.ibatis.annotations.*;

public interface PassengerMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO passenger (first_name, last_name, passport) " +
            "VALUES (#{firstName}, #{lastName}, #{passport})")
    Integer insert(Passenger passenger);

    @Select("SELECT * FROM passenger WHERE first_name=#{firstName} AND last_name=#{lastName} AND passport=#{passport}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "passport", column = "passport"),
    })
    Passenger find(Passenger passenger);

    @Delete("DELETE FROM passenger")
    Integer deleteAll();
}
