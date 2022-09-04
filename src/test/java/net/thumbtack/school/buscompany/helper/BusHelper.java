package net.thumbtack.school.buscompany.helper;


import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Bus;
import net.thumbtack.school.buscompany.service.DebugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusHelper {
    @Autowired
    private DebugService debugService;

    public void generateDefaultBuses() throws ServerException {
        debugService.insertBus(new Bus("Пазик", 21));
        debugService.insertBus(new Bus("Ikarus", 40));
    }
}
