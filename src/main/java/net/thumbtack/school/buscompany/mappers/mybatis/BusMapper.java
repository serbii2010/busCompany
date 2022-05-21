package net.thumbtack.school.buscompany.mappers.mybatis;

import net.thumbtack.school.buscompany.model.Bus;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BusMapper {
    @Select("SELECT * FROM bus")
    @Result(property = "placeCount", column = "place_count")
    List<Bus> findAll();
}
