package net.thumbtack.school.buscompany.mappers.dto.shop;

import net.thumbtack.school.buscompany.dto.response.shop.BusDtoResponse;
import net.thumbtack.school.buscompany.model.Bus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BusMapper {
    BusMapper INSTANCE = Mappers.getMapper(BusMapper.class);

    @Mapping(target = "busName", source = "name")
    BusDtoResponse busToDto(Bus bus);
}
