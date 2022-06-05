package net.thumbtack.school.buscompany.mappers.mybatis.trip;

import net.thumbtack.school.buscompany.model.DateTrip;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface DateTripMapper {
    @Insert("INSERT INTO date_trip (trip_id, date) " +
            "VALUES (#{trip.id}, #{date})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(DateTrip date);

    @Delete("DELETE FROM date_trip WHERE id=#{id}")
    void delete(DateTrip dateTrip);

    @Delete("DELETE FROM date_trip WHERE trip_id = #{tripId}")
    void deleteByTripId(String tripId);
}
