package net.thumbtack.school.buscompany.service.trip;

import net.thumbtack.school.buscompany.daoImpl.trip.StationDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationService {
    @Autowired
    private StationDaoImpl stationDao;

    public Station findStationByName(String name) throws ServerException {
        Station station = stationDao.findByName(name);
        if (station == null) {
            throw new ServerException(ServerErrorCode.STATION_NOT_FOUND);
        }
        return station;
    }
}
