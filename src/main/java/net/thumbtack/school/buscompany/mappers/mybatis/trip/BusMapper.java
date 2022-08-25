package net.thumbtack.school.buscompany.mappers.mybatis.trip;

import net.thumbtack.school.buscompany.model.Bus;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BusMapper {
    @Select("SELECT * FROM bus")
    @Result(property = "placeCount", column = "place_count")
    List<Bus> findAll();

    @Select("SELECT * FROM bus WHERE name=#{name}")
    @Result(property = "placeCount", column = "place_count")
    Bus findByName(String name);

    @Delete("DELETE FROM bus")
    Integer deleteAll();

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO bus (name, place_count) " +
            "VALUES (#{name}, #{placeCount})")
    Integer insert(Bus bus);

    @Update("ALTER TABLE bus AUTO_INCREMENT = 1")
    void resetAutoIncrement();
}
