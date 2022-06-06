package net.thumbtack.school.buscompany.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {
    private int id;
    private String firstName;
    private String lastName;
    private String passport;
}
