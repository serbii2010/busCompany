package net.thumbtack.school.buscompany.helper;

import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Station;
import net.thumbtack.school.buscompany.service.DebugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StationHelper {
    @Autowired
    private DebugService debugService;
    public void generateDefaultStation() throws ServerException {
        debugService.insertStation(new Station("Omsk"));
        debugService.insertStation(new Station("Новосибирск"));
    }
}
