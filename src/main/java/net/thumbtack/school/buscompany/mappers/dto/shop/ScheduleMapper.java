package net.thumbtack.school.buscompany.mappers.dto.shop;

import net.thumbtack.school.buscompany.dto.request.shop.ScheduleDto;
import net.thumbtack.school.buscompany.model.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    Schedule scheduleDtoToSchedule(ScheduleDto request);
}
