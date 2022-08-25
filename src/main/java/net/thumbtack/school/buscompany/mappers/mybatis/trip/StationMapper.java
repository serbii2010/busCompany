package net.thumbtack.school.buscompany.mappers.mybatis.trip;

import net.thumbtack.school.buscompany.model.Bus;
import net.thumbtack.school.buscompany.model.Station;
import org.apache.ibatis.annotations.*;

public interface StationMapper {
    @Select("SELECT id, name FROM station WHERE name=#{name}")
    Station findByName(@Param("name") String name);

    @Delete("DELETE FROM station")
    Integer deleteAll();

    @Update("ALTER TABLE station AUTO_INCREMENT = 1")
    void resetAutoIncrement();

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO station (name) " +
            "VALUES (#{name})")
    Integer insert(Station station);
}
