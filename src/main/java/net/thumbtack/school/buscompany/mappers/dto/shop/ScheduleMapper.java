package net.thumbtack.school.buscompany.mappers.dto.shop;

import net.thumbtack.school.buscompany.dto.request.shop.ScheduleDto;
import net.thumbtack.school.buscompany.model.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    @Mapping(target = "fromDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "toDate", dateFormat = "yyyy-MM-dd")
    Schedule scheduleDtoToSchedule(ScheduleDto request);
}
