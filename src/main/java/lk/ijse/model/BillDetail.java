package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class BillDetail {

    private String billId;
    private String medicineId;
    private int medicineQty;
    private double medicinePrice;

}
