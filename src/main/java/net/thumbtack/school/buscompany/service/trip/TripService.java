package net.thumbtack.school.buscompany.service.trip;

import net.thumbtack.school.buscompany.daoImpl.trip.DateTripDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.trip.ScheduleDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.trip.TripDaoImpl;
import net.thumbtack.school.buscompany.dto.request.trip.TripDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EmptyDtoResponse;
import net.thumbtack.school.buscompany.dto.response.trip.BaseTripDtoResponse;
import net.thumbtack.school.buscompany.dto.response.trip.TripAdminDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.mappers.dto.trip.TripMapper;
import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Trip;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TripService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private StationService stationService;
    @Autowired
    private BusService busService;
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TripDaoImpl tripDao;
    @Autowired
    private ScheduleDaoImpl scheduleDao;
    @Autowired
    private DateTripDaoImpl dateTripDao;

    public TripAdminDtoResponse addTrip(String javaSessionId, TripDtoRequest request) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        accountService.checkAdmin(account);
        Trip trip = TripMapper.INSTANCE.tripDtoToTrip(request, stationService, busService);
        insert(trip);
        return TripMapper.INSTANCE.tripAdminToDtoResponse(trip);
    }

    public TripAdminDtoResponse getTrip(String javaSessionId, String id) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        accountService.checkAdmin(account);

        Trip trip = findById(id);
        return TripMapper.INSTANCE.tripAdminToDtoResponse(trip);
    }

    public TripAdminDtoResponse update(String javaSessionId, String id, TripDtoRequest tripDtoRequest) throws ServerException {
        accountService.checkAdmin(accountService.getAuthAccount(javaSessionId));
        if (tripDtoRequest.getDates() != null) {
            checkDuplicates(tripDtoRequest.getDates());
        }
        Trip trip = findById(id);
        checkNotApproved(trip);
        TripMapper.INSTANCE.update(trip, tripDtoRequest, stationService, busService, this);
        if (trip.getSchedule() != null) {
            trip.getSchedule().setTrip(trip);
        }
        tripDao.update(trip);
        return TripMapper.INSTANCE.tripAdminToDtoResponse(trip);
    }

    public TripAdminDtoResponse approved(String javaSessionId, String id) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        accountService.checkAdmin(account);
        Trip trip = findById(id);
        trip.setApproved(true);
        tripDao.update(trip);
        return TripMapper.INSTANCE.tripAdminToDtoResponse(trip);
    }

    public EmptyDtoResponse delete(String javaSessionId, String id) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        accountService.checkAdmin(account);
        Trip trip = findById(id);
        checkNotApproved(trip);
        tripDao.remove(trip);
        return new EmptyDtoResponse();
    }

    public List<? extends BaseTripDtoResponse> filter(String javaSessionId, String fromStation, String toStation, String busName, String fromDate, String toDate)  throws ServerException {
        List<? extends BaseTripDtoResponse> result;
        Account account = accountService.getAuthAccount(javaSessionId);
        if (accountService.isAdmin(account)) {
            List<Trip> listTrip = getListTripByAdmin(fromStation, toStation, busName, fromDate, toDate);
            result = TripMapper.INSTANCE.tripListAdminToDtoResponse(listTrip);
        } else if (accountService.isClient(account)) {
            List<Trip> listTrip = getListTripByClient(fromStation, toStation, busName, fromDate, toDate);
            result = TripMapper.INSTANCE.tripListClientToDtoResponse(listTrip);
        } else {
            result = new ArrayList<>();
        }
        return result;
    }

    public Trip findById(String id) throws ServerException {
        Trip trip = tripDao.findById(id);
        if (trip == null) {
            throw new ServerException(ServerErrorCode.TRIP_NOT_FOUND);
        }
        return trip;
    }

    public void checkApproved(Trip trip) throws ServerException {
        if (!trip.isApproved()) {
            throw new ServerException(ServerErrorCode.ACTION_FORBIDDEN);
        }
    }

    public void checkNotApproved(Trip trip) throws ServerException {
        if (trip.isApproved()) {
            throw new ServerException(ServerErrorCode.ACTION_FORBIDDEN);
        }
    }

    public DateTrip findDateTrip(String tripId, String date) throws ServerException {
        DateTrip dateTrip = dateTripDao.find(tripId, date);
        if (dateTrip == null) {
            throw new ServerException(ServerErrorCode.DATE_TRIP_NOT_FOUND);
        }
        return  dateTrip;
    }

    public List<Trip> getListTripByAdmin(String fromStation, String toStation, String busName, String fromDate, String toDate) throws ServerException {
        return tripDao.filter(fromStation, toStation, busName, fromDate, toDate, null);
    }

    public List<Trip> getListTripByClient(String fromStation, String toStation, String busName, String fromDate, String toDate) throws ServerException {
        return tripDao.filter(fromStation, toStation, busName, fromDate, toDate, true);
    }


    public Trip insert(Trip trip) throws ServerException {
        if (trip.getDates() == null) {
            List<DateTrip> dates = this.generateDates(trip);
            trip.setDates(dates);
        } else {
            checkDuplicates(trip.getDates().stream().map(dateTrip -> dateTrip.getDate().toString()).collect(Collectors.toList()));
        }
        tripDao.insert(trip);
        if (trip.getSchedule() != null) {
            trip.getSchedule().setTrip(trip);
            scheduleDao.insert(trip.getSchedule());
        }
        return trip;
    }

    public List<DateTrip> updateDates(Trip trip, List<String> datesNew) throws ServerException {
        for (DateTrip dateTrip: trip.getDates()) {
            dateTripDao.remove(dateTrip);
        }
        if (datesNew != null) {
            checkDuplicates(datesNew);
            return datesNew.stream().map(date -> new DateTrip(trip, LocalDate.parse(date))).collect(Collectors.toList());
        }

        return this.generateDates(trip);
    }

    private List<DateTrip> generateDates(Trip trip) {
        List<DateTrip> dates = new ArrayList<>();

        LocalDate dateStart = trip.getSchedule().getFromDate();
        LocalDate dateEnd = trip.getSchedule().getToDate();

        for (LocalDate date = dateStart; date.isBefore(dateEnd.plusDays(1)); date = date.plusDays(1)) {
            if (trip.getSchedule().getPeriod().equals("daily")) {
                dates.add(new DateTrip(trip, date));
                continue;
            }
            if (trip.getSchedule().getPeriod().equals("odd")) {
                if (date.getDayOfMonth() % 2 == 1) {
                    dates.add(new DateTrip(trip, date));
                    continue;
                }
            }
            if (trip.getSchedule().getPeriod().equals("even")) {
                if (date.getDayOfMonth() % 2 == 0) {
                    dates.add(new DateTrip(trip, date));
                    continue;
                }
            }
            if (Arrays.asList(trip.getSchedule().getPeriod().split(","))
                    .contains(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH))) {
                dates.add(new DateTrip(trip, date));
                continue;
            }
            if (Arrays.asList(trip.getSchedule().getPeriod().split(",")).contains(String.valueOf(date.getDayOfMonth()))) {
                dates.add(new DateTrip(trip, date));
            }
        }
        return dates;
    }

    private void checkDuplicates(List<String> dates) throws ServerException {
        Set<String> setDates = new HashSet<>(dates);
        if (dates.size() > setDates.size()) {
            throw new ServerException(ServerErrorCode.DATES_TRIP_DUPLICATED);
        }
    }
}
