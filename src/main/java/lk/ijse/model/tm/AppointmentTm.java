package lk.ijse.model.tm;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class AppointmentTm {

    private String id;
    private String clientId;
    private int no;
    private String date;
    private String time;

}
