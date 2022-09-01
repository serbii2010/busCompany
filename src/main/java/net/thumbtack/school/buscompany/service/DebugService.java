package net.thumbtack.school.buscompany.service;

import net.thumbtack.school.buscompany.daoImpl.DebugDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.trip.BusDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.trip.StationDaoImpl;
import net.thumbtack.school.buscompany.model.Bus;
import net.thumbtack.school.buscompany.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Profile("dev")
@Transactional
public class DebugService {
    @Autowired
    private DebugDaoImpl debugDao;
    @Autowired
    private BusDaoImpl busDao;
    @Autowired
    private StationDaoImpl stationDao;

    public void clear() {
        debugDao.clear();
    }

    public void insertBus(Bus bus) {
        busDao.insert(bus);
    }

    public void insertStation(Station station) {
        stationDao.insert(station);
    }
}
