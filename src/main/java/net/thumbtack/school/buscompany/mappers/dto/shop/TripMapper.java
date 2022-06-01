package net.thumbtack.school.buscompany.mappers.dto.shop;

import net.thumbtack.school.buscompany.dto.request.shop.CreateTripDtoRequest;
import net.thumbtack.school.buscompany.dto.response.shop.CreateTripDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Trip;
import net.thumbtack.school.buscompany.service.BusService;
import net.thumbtack.school.buscompany.service.ScheduleService;
import net.thumbtack.school.buscompany.service.StationService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring", imports = {StationService.class, BusService.class, ScheduleService.class})
public interface TripMapper {
    TripMapper INSTANCE = Mappers.getMapper(TripMapper.class);

    @Mapping(target = "bus", expression = "java(busService.findByName(request.getBusName()))")
    @Mapping(target = "fromStation", expression = "java(stationService.findStationByName(request.getFromStation()))")
    @Mapping(target = "toStation", expression = "java(stationService.findStationByName(request.getToStation()))")
    @Mapping(target = "schedule", expression =
            "java(scheduleService.findOrInsert(ScheduleMapper.INSTANCE.scheduleDtoToSchedule(request.getSchedule())))")
    @Mapping(target = "dates", dateFormat = "yyyy-MM-dd")
    Trip createTripDtoToTrip(CreateTripDtoRequest request,
                             @Context StationService stationService,
                             @Context BusService busService,
                             @Context ScheduleService scheduleService) throws ServerException;


    @Mapping(target = "fromStation", source = "fromStation.name")
    @Mapping(target = "toStation", source = "toStation.name")
    @Mapping(target = "tripId", source = "id")
    @Mapping(target = "bus.busName", source = "bus.name")
    @Mapping(target = "bus.places", source = "bus.placeCount")
    @Mapping(target = "schedule.fromDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "schedule.toDate", dateFormat = "yyyy-MM-dd")
    CreateTripDtoResponse tripToDtoResponse(Trip trip);

    @Mapping(target = "date", dateFormat = "yyyy-MM-dd")
    DateTrip stringToDate(String date);

    default String map(DateTrip value) {
        return new SimpleDateFormat( "yyyy-MM-dd" ).format(value.getDate());
    }
}
