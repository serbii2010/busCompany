package net.thumbtack.school.buscompany.helper;

import lombok.Getter;
import net.thumbtack.school.buscompany.model.Order;

import java.text.ParseException;

@Getter
public class OrderHelper {
    private static OrderHelper instance = null;

    private OrderHelper() throws ParseException {
        init();
    }

    private Order order;

    public static OrderHelper getInstance() throws ParseException {
        if (instance == null) {
            instance = new OrderHelper();
        }
        return instance;
    }

    public void init() throws ParseException {
        order = new Order(1, DateTripHelper.getInstance().getDateTrip(TripHelper.getInstance().getTrip()), AccountHelper.getInstance().getClient(), null);
    }
}
