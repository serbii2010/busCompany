package net.thumbtack.school.buscompany.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Bus {
    private int id;
    private String name;
    private int placeCount;

    public Bus(String name, int placeCount) {
        this(0, name, placeCount);
    }
}
