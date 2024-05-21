package lk.ijse.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode


public class SupplierTm {

    private String id;
    private String name;
    private int contactNo;
    private String location;
    private String email;
    private String productType;
    private int qtyOnHand;

}
