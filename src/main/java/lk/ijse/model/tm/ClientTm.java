package lk.ijse.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class ClientTm {

    private String id;
    private String petId;
    private String name;
    private String address;
    private String email;
    private int contactNo;


}
