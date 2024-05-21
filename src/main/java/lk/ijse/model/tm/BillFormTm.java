package lk.ijse.model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class BillFormTm {


    private String mId;
    private String mName;
    private String mType;
    private double mPrice;
    private int mQty;
    private double total;
    private JFXButton btnRemove;

}
