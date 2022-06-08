package net.thumbtack.school.buscompany.mappers.mybatis.trip;

import net.thumbtack.school.buscompany.model.Schedule;
import org.apache.ibatis.annotations.*;

public interface ScheduleMapper {
    @Insert("INSERT INTO schedule (from_date, to_date, periods) " +
            "VALUES (#{schedule.fromDate}, #{schedule.toDate}, #{schedule.period})")
    @Options(useGeneratedKeys = true, keyProperty = "schedule.id")
    Integer insert(@Param("schedule") Schedule schedule);

    @Select("SELECT * FROM schedule WHERE from_date=#{fromDate} AND to_date=#{toDate} AND periods=#{period}")
    @Results(value = {
            @Result(property = "fromDate", column = "from_date"),
            @Result(property = "toDate", column = "to_date"),
            @Result(property = "period", column = "periods"),
    })
    Schedule find(Schedule schedule);
}
