package lk.ijse.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class PrescriptionTm {

    private String id;
    private String type;
    private String veterinarianId;

}
