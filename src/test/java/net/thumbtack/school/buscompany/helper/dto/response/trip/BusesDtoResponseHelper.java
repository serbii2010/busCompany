package net.thumbtack.school.buscompany.helper.dto.response.trip;

import net.thumbtack.school.buscompany.dto.response.trip.BusDtoResponse;

import java.util.ArrayList;
import java.util.List;

public class BusesDtoResponseHelper {
    public static List<BusDtoResponse> get() {
        List<BusDtoResponse> buses = new ArrayList<>();
        buses.add(new BusDtoResponse("Пазик", 21));
        buses.add(new BusDtoResponse("Ikarus", 40));
        return buses;
    }
}
