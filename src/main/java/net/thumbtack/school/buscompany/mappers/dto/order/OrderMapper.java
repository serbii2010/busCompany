package net.thumbtack.school.buscompany.mappers.dto.order;

import net.thumbtack.school.buscompany.dto.request.order.OrderDtoRequest;
import net.thumbtack.school.buscompany.dto.response.order.OrderDtoResponse;
import net.thumbtack.school.buscompany.dto.response.trip.TripDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.Trip;
import net.thumbtack.school.buscompany.service.trip.TripService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", imports = {TripService.class, Account.class})
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "trip", expression = "java(tripService.findById(String.valueOf(request.getTripId())))")
    @Mapping(target = "account", expression = "java(account)")
    Order dtoToOrder(OrderDtoRequest request,
                     @Context TripService tripService,
                     @Context Account account) throws ServerException;

    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "tripId", source = "trip.id")
    @Mapping(target = "fromStation", source = "trip.fromStation.name")
    @Mapping(target = "toStation", source = "trip.toStation.name")
    @Mapping(target = "busName", source = "trip.bus.name")
    @Mapping(target = "start", source = "trip.start")
    @Mapping(target = "price", source = "trip.price")
    @Mapping(target = "totalPrice", expression = "java(object.getPassengers().size()*object.getTrip().getPrice())")
    OrderDtoResponse orderToDto(Order object);

    List<OrderDtoResponse> orderListToDtoResponse(List<Order> trips);
}
