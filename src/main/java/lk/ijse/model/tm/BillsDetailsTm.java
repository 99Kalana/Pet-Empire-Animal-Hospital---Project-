package lk.ijse.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class BillsDetailsTm {

    private String billId;
    private String medicineId;
    private int medicineQty;
    private double medicinePrice;

}
