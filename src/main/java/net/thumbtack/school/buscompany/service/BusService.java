package net.thumbtack.school.buscompany.service;

import net.thumbtack.school.buscompany.daoImpl.shop.BusDaoImpl;
import net.thumbtack.school.buscompany.model.Bus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusService.class);

    @Autowired
    private BusDaoImpl busDao;

    public List<Bus> getBuses() {
        return busDao.findAll();
    }
}
