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

import java.text.SimpleDateFormat;
import java.util.List;

@Mapper(componentModel = "spring", imports = {StationService.class, BusService.class,
        ScheduleService.class, TripService.class})
public interface TripMapper {
    TripMapper INSTANCE = Mappers.getMapper(TripMapper.class);

    @Mapping(target = "bus", expression = "java(busService.findByName(request.getBusName()))")
    @Mapping(target = "fromStation", expression = "java(stationService.findStationByName(request.getFromStation()))")
    @Mapping(target = "toStation", expression = "java(stationService.findStationByName(request.getToStation()))")
    @Mapping(target = "schedule", expression =
            "java(scheduleService.findOrInsert(ScheduleMapper.INSTANCE.scheduleDtoToSchedule(request.getSchedule())))")
    @Mapping(target = "dates", dateFormat = "yyyy-MM-dd")
    Trip tripDtoToTrip(TripDtoRequest request,
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
    TripAdminDtoResponse tripAdminToDtoResponse(Trip trip);

    List<TripAdminDtoResponse> tripListAdminToDtoResponse(List<Trip> trips);

    @Mapping(target = "fromStation", source = "fromStation.name")
    @Mapping(target = "toStation", source = "toStation.name")
    @Mapping(target = "tripId", source = "id")
    @Mapping(target = "bus.busName", source = "bus.name")
    @Mapping(target = "bus.places", source = "bus.placeCount")
    @Mapping(target = "schedule.fromDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "schedule.toDate", dateFormat = "yyyy-MM-dd")
    TripClientDtoResponse tripClientToDtoResponse(Trip trip);

    List<TripClientDtoResponse> tripListClientToDtoResponse(List<Trip> trips);


    @Mapping(target = "bus", expression = "java(busService.findByName(request.getBusName()))")
    @Mapping(target = "fromStation", expression = "java(stationService.findStationByName(request.getFromStation()))")
    @Mapping(target = "toStation", expression = "java(stationService.findStationByName(request.getToStation()))")
    @Mapping(target = "schedule", expression =
            "java(scheduleService.findOrInsert(ScheduleMapper.INSTANCE.scheduleDtoToSchedule(request.getSchedule())))")
    @Mapping(target = "dates", expression = "java(tripService.updateDates(trip))")
    void update(@MappingTarget Trip trip, TripDtoRequest request,
                @Context StationService stationService,
                @Context BusService busService,
                @Context ScheduleService scheduleService,
                @Context TripService tripService) throws ServerException;

    @Mapping(target = "date", dateFormat = "yyyy-MM-dd")
    DateTrip stringToDate(String date);

    default String map(DateTrip value) {
        return new SimpleDateFormat("yyyy-MM-dd").format(value.getDate());
    }
}
