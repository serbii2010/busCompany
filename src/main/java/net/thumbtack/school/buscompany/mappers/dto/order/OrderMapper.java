package net.thumbtack.school.buscompany.mappers.dto.order;

import net.thumbtack.school.buscompany.dto.request.order.OrderDtoRequest;
import net.thumbtack.school.buscompany.dto.response.order.OrderDtoResponse;
import net.thumbtack.school.buscompany.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order dtoToOrder(OrderDtoRequest request);

    OrderDtoResponse orderToDto(Order object);
}
