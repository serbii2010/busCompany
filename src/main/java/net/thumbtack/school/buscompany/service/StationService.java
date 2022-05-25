package net.thumbtack.school.buscompany.service;

import net.thumbtack.school.buscompany.daoImpl.shop.StationDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationService {
    @Autowired
    private StationDaoImpl stationDao;

    public Station findStationByName(String name) throws ServerException {
        return stationDao.findByName(name);
    }
}
