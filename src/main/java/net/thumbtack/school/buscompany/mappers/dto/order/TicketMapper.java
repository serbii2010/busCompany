package net.thumbtack.school.buscompany.mappers.dto.order;

import net.thumbtack.school.buscompany.dto.response.order.TicketDtoResponse;
import net.thumbtack.school.buscompany.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    @Mapping(target = "firstName", source = "orderPassenger.passenger.firstName")
    @Mapping(target = "lastName", source = "orderPassenger.passenger.lastName")
    @Mapping(target = "passport", source = "orderPassenger.passenger.passport")
    @Mapping(target = "place", source = "place")
    @Mapping(target = "orderId", source = "orderPassenger.order.id")
    @Mapping(target = "ticket", expression = "java(String.format(\"%s_%s\", ticket.getOrderPassenger().getOrder().getDateTrip().getTrip().getId(), ticket.getPlace()))")
    TicketDtoResponse tickerToDto(Ticket ticket);
}
