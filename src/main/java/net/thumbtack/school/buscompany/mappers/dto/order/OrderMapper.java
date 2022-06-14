package net.thumbtack.school.buscompany.mappers.dto.order;

import net.thumbtack.school.buscompany.dto.request.order.OrderDtoRequest;
import net.thumbtack.school.buscompany.dto.response.order.OrderDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.service.trip.TripService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", imports = {TripService.class, Account.class})
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "dateTrip",
            expression = "java(tripService.findDateTrip(String.valueOf(request.getTripId()), request.getDate()))")
    @Mapping(target = "account", expression = "java(account)")
    Order dtoToOrder(OrderDtoRequest request,
                     @Context TripService tripService,
                     @Context Client account) throws ServerException;

    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "tripId", source = "dateTrip.trip.id")
    @Mapping(target = "fromStation", source = "dateTrip.trip.fromStation.name")
    @Mapping(target = "toStation", source = "dateTrip.trip.toStation.name")
    @Mapping(target = "busName", source = "dateTrip.trip.bus.name")
    @Mapping(target = "start", source = "dateTrip.trip.start")
    @Mapping(target = "price", source = "dateTrip.trip.price")
    @Mapping(target = "totalPrice", expression = "java(object.getPassengers().size()*object.getDateTrip().getTrip().getPrice())")
    OrderDtoResponse orderToDto(Order object);

    List<OrderDtoResponse> orderListToDtoResponse(List<Order> trips);
}
