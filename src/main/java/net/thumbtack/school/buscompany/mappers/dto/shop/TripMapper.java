package net.thumbtack.school.buscompany.mappers.dto.shop;

import net.thumbtack.school.buscompany.dto.request.shop.CreateTripDtoRequest;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Trip;
import net.thumbtack.school.buscompany.service.StationService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", imports = {StationService.class})
public interface TripMapper {
    TripMapper INSTANCE = Mappers.getMapper(TripMapper.class);

    @Mapping(target = "fromStation", expression = "java(stationService.findStationByName(request.getFromStation()))")
    @Mapping(target = "toStation", expression = "java(stationService.findStationByName(request.getToStation()))")
    Trip createTripDtoToTrip(CreateTripDtoRequest request,  @Context StationService stationService) throws ServerException;


}
