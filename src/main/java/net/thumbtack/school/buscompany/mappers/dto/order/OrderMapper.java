package net.thumbtack.school.buscompany.mappers.dto.order;

import net.thumbtack.school.buscompany.dto.request.order.OrderDtoRequest;
import net.thumbtack.school.buscompany.dto.response.order.OrderDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.service.trip.TripService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", imports = {TripService.class})
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "trip", expression = "java(tripService.findById(String.valueOf(request.getTripId())))")
    Order dtoToOrder(OrderDtoRequest request,
                     @Context TripService tripService) throws ServerException;

    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "tripId", source = "trip.id")
    @Mapping(target = "fromStation", source = "trip.fromStation.name")
    @Mapping(target = "toStation", source = "trip.toStation.name")
    @Mapping(target = "busName", source = "trip.bus.name")
    @Mapping(target = "start", source = "trip.start")
    @Mapping(target = "price", source = "trip.price")
    @Mapping(target = "totalPrice", expression = "java(object.getPassengers().size()*object.getTrip().getPrice())")
    OrderDtoResponse orderToDto(Order object);
}
