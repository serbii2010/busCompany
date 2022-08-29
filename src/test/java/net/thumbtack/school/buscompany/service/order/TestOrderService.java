package net.thumbtack.school.buscompany.service.order;

import net.thumbtack.school.buscompany.daoImpl.order.OrderDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.OrderHelper;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.account.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class TestOrderService {
    @Autowired
    private OrderHelper orderHelper;
    @Autowired
    private AccountHelper accountHelper;

    @Mock
    private OrderDaoImpl orderDao;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void init() {
        accountHelper.init();
        orderHelper.init();
    }

    @Test
    void testFindById() throws Exception {
        Mockito.when(orderDao.findById("1")).thenReturn(orderHelper.getOrder());
        Order order = orderService.findById("1");
        assertEquals(orderHelper.getOrder(), order);
    }

    @Test
    void testFindById_notFound() throws Exception {
        Mockito.when(orderDao.findById("1")).thenThrow(new ServerException(ServerErrorCode.ORDER_NOT_FOUND));
        assertThrows(ServerException.class, () -> orderService.findById("1"));
    }

    @Test
    void checkApproved() {
        Order order = orderHelper.getOrder();
        order.getDateTrip().getTrip().setApproved(true);
        assertDoesNotThrow(() -> orderService.checkApproved(order));
    }

    @Test
    void checkApproved_notApproved() {
        Order order = orderHelper.getOrder();
        order.getDateTrip().getTrip().setApproved(false);
        assertThrows(ServerException.class, () -> orderService.checkApproved(order));
    }

    @Test
    void checkAccount() {
        Client client = accountHelper.getClient();
        Order order = orderHelper.getOrder();
        assertDoesNotThrow(() -> orderService.checkAccount(order, client));
    }

    @Test
    void checkAccount_bad() {
        Order order = orderHelper.getOrder();
        accountHelper.init();
        Client client = accountHelper.getClient();
        client.setId(2);
        assertThrows(ServerException.class, () -> orderService.checkAccount(order, client));
    }
}