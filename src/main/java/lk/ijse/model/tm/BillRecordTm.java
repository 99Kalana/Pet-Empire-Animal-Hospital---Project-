package lk.ijse.model.tm;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class BillRecordTm {

    private String billId;
    private String clientId;
    private String date;

}
