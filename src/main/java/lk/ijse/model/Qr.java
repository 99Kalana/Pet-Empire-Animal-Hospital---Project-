package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Qr {

    private String clientId;
    private String petId;
    private String clientName;
    private String clientEmail;
    private int clientContactNo;


}
