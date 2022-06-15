package net.thumbtack.school.buscompany.mappers.dto.order;

import net.thumbtack.school.buscompany.dto.response.order.TicketDtoResponse;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.Passenger;
import net.thumbtack.school.buscompany.model.Place;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    @Mapping(target = "firstName", source = "passenger.firstName")
    @Mapping(target = "lastName", source = "passenger.lastName")
    @Mapping(target = "passport", source = "passenger.passport")
    @Mapping(target = "place", source = "place.number")
    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "ticket", expression = "java(String.format(\"%s_%s\", order.getDateTrip().getId(), place.getNumber()))")
    TicketDtoResponse tickerToDto(@Param("place") Place place, @Param("order") Order order, @Param("passenger") Passenger passenger);
}
