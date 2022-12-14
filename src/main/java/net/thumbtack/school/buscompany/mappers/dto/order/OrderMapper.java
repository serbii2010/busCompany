package net.thumbtack.school.buscompany.mappers.dto.order;

import net.thumbtack.school.buscompany.dto.request.order.OrderDtoRequest;
import net.thumbtack.school.buscompany.dto.response.order.OrderDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.service.trip.TripService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.text.SimpleDateFormat;
import java.util.List;

@Mapper(componentModel = "spring", imports = {TripService.class, Account.class, SimpleDateFormat.class})
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "dateTrip",
            expression = "java(tripService.findDateTrip(String.valueOf(request.getTripId()), request.getDate()))")
    @Mapping(target = "client", expression = "java(client)")
    Order dtoToOrder(OrderDtoRequest request,
                     @Context TripService tripService,
                     @Context Client client) throws ServerException;

    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "tripId", source = "dateTrip.trip.id")
    @Mapping(target = "fromStation", source = "dateTrip.trip.fromStation.name")
    @Mapping(target = "toStation", source = "dateTrip.trip.toStation.name")
    @Mapping(target = "busName", source = "dateTrip.trip.bus.name")
    @Mapping(target = "start", source = "dateTrip.trip.start", dateFormat = "HH:mm")
    @Mapping(target = "duration", expression = "java( String.format(\"%d:%d\", order.getDateTrip().getTrip().getDuration()/60, order.getDateTrip().getTrip().getDuration()%60) )")
    @Mapping(target = "price", source = "dateTrip.trip.price")
    @Mapping(target = "date", source = "dateTrip.date", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "totalPrice",
            expression = "java(order.getPassengers().size()*order.getDateTrip().getTrip().getPrice())")
    OrderDtoResponse orderToDto(Order order);

    List<OrderDtoResponse> orderListToDtoResponse(List<Order> trips);
}
