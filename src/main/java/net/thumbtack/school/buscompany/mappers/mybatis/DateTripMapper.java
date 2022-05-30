package net.thumbtack.school.buscompany.mappers.mybatis;

import net.thumbtack.school.buscompany.model.DateTrip;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface DateTripMapper {
    @Insert("INSERT INTO date_trip (trip_id, date) " +
            "VALUES (#{trip.id}, #{date})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(DateTrip date);
}
