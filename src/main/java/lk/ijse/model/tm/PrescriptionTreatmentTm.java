package lk.ijse.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class PrescriptionTreatmentTm {

    private String pId;
    private String tId;
    private double price;
    private String date;
    private String time;

}
