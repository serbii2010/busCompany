package net.thumbtack.school.buscompany;

import net.thumbtack.school.buscompany.service.DebugService;
import net.thumbtack.school.buscompany.service.trip.BusService;
import net.thumbtack.school.buscompany.utils.MyBatisUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;


public class TestBase {
    @Autowired
    private DebugService debugService;
    @Autowired
    private BusService busService;

    private static boolean setUpIsDone = false;

    @BeforeAll()
    public static void setUp() {
        if (!setUpIsDone) {
            boolean initSqlSessionFactory = MyBatisUtils.initSqlSessionFactory();
            if (!initSqlSessionFactory) {
                throw new RuntimeException("Can't create connection, stop");
            }
            setUpIsDone = true;
        }
    }

    @BeforeEach()
    public void clearDatabase() {
        debugService.clear();
    }

}
