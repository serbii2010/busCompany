package net.thumbtack.school.buscompany.service;

import net.thumbtack.school.buscompany.daoImpl.DebugDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebugService {
    @Autowired
    private DebugDaoImpl debugDao;

    public void clear() {
        debugDao.clear();
    }
}
