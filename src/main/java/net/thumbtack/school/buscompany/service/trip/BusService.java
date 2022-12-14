package net.thumbtack.school.buscompany.service.trip;

import net.thumbtack.school.buscompany.daoImpl.trip.BusDaoImpl;
import net.thumbtack.school.buscompany.dto.response.trip.BusDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.mappers.dto.trip.BusMapper;
import net.thumbtack.school.buscompany.model.Bus;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.service.account.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BusService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusService.class);

    @Autowired
    private BusDaoImpl busDao;
    @Autowired
    private AccountService accountService;

    public List<BusDtoResponse> getBuses(String javaSessionId) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        accountService.checkAdmin(account);

        List<BusDtoResponse> responseList = new ArrayList<>();
        for (Bus bus: busDao.findAll()) {
            responseList.add(BusMapper.INSTANCE.busToDto(bus));
        }
        return responseList;
    }

    public Bus findByName(String name) throws ServerException {
        Bus bus = busDao.findByName(name);
        if (bus == null) {
            throw new ServerException(ServerErrorCode.BUS_NOT_FOUND);
        }
        return bus;
    }
}
