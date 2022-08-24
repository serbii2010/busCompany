package net.thumbtack.school.buscompany.mappers.mybatis.trip;

import net.thumbtack.school.buscompany.model.Station;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface StationMapper {
    @Select("SELECT id, name FROM station WHERE name=#{name}")
    Station findByName(@Param("name") String name);

    @Delete("DELETE FROM station")
    Integer deleteAll();

    @Update("ALTER TABLE station AUTO_INCREMENT = 1")
    void resetAutoIncrement();
}
