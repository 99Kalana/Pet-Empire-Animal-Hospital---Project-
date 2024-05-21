package lk.ijse.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class MedicineTm {

    private String id;
    private String name;
    private String type;
    private double price;
    private String description;
    private int qtyOnHand;

}
