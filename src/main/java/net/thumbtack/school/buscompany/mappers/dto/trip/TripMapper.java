package net.thumbtack.school.buscompany.mappers.dto.trip;

import net.thumbtack.school.buscompany.dto.request.trip.TripDtoRequest;
import net.thumbtack.school.buscompany.dto.response.trip.TripAdminDtoResponse;
import net.thumbtack.school.buscompany.dto.response.trip.TripClientDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Trip;
import net.thumbtack.school.buscompany.service.trip.BusService;
import net.thumbtack.school.buscompany.service.trip.ScheduleService;
import net.thumbtack.school.buscompany.service.trip.StationService;
import net.thumbtack.school.buscompany.service.trip.TripService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", imports = {StationService.class, BusService.class,
        ScheduleService.class, TripService.class})
public interface TripMapper {
    TripMapper INSTANCE = Mappers.getMapper(TripMapper.class);

    @Mapping(target = "bus", expression = "java(busService.findByName(request.getBusName()))")
    @Mapping(target = "fromStation", expression = "java(stationService.findStationByName(request.getFromStation()))")
    @Mapping(target = "toStation", expression = "java(stationService.findStationByName(request.getToStation()))")
    @Mapping(target = "dates", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "duration", expression = "java(Integer.parseInt(request.getDuration().split(\":\")[0])*60 + Integer.parseInt(request.getDuration().split(\":\")[1]) )")
    Trip tripDtoToTrip(TripDtoRequest request,
                       @Context StationService stationService,
                       @Context BusService busService) throws ServerException;



    @Mapping(target = "fromStation", source = "fromStation.name")
    @Mapping(target = "toStation", source = "toStation.name")
    @Mapping(target = "tripId", source = "id")
    @Mapping(target = "bus.busName", source = "bus.name")
    @Mapping(target = "bus.places", source = "bus.placeCount")
    @Mapping(target = "schedule.fromDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "schedule.toDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "duration", expression = "java( String.format(\"%d:%d\", trip.getDuration()/60, trip.getDuration()%60) )")
    TripAdminDtoResponse tripAdminToDtoResponse(Trip trip);

    List<TripAdminDtoResponse> tripListAdminToDtoResponse(List<Trip> trips);

    @Mapping(target = "fromStation", source = "fromStation.name")
    @Mapping(target = "toStation", source = "toStation.name")
    @Mapping(target = "tripId", source = "id")
    @Mapping(target = "bus.busName", source = "bus.name")
    @Mapping(target = "bus.places", source = "bus.placeCount")
    @Mapping(target = "schedule.fromDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "schedule.toDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "duration", expression = "java( String.format(\"%d:%d\", trip.getDuration()/60, trip.getDuration()%60) )")
    TripClientDtoResponse tripClientToDtoResponse(Trip trip);

    List<TripClientDtoResponse> tripListClientToDtoResponse(List<Trip> trips);


    @Mapping(target = "bus", expression = "java(busService.findByName(request.getBusName()))")
    @Mapping(target = "fromStation", expression = "java(stationService.findStationByName(request.getFromStation()))")
    @Mapping(target = "toStation", expression = "java(stationService.findStationByName(request.getToStation()))")
    @Mapping(target = "dates", expression = "java(tripService.updateDates(trip))")
    @Mapping(target = "duration", expression = "java(Integer.parseInt(request.getDuration().split(\":\")[0])*60 + Integer.parseInt(request.getDuration().split(\":\")[1]) )")
    void update(@MappingTarget Trip trip, TripDtoRequest request,
                @Context StationService stationService,
                @Context BusService busService,
                @Context TripService tripService) throws ServerException;

    @Mapping(target = "date", dateFormat = "yyyy-MM-dd")
    DateTrip stringToDate(String date);

    default String setTime(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    default String map(DateTrip value) {
        return value.getDate().toString();
    }
}
