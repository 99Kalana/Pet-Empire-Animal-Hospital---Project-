package lk.ijse.model;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Bill {

    private String billId;
    private String clientId;
    private Date date;


}
