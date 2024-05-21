package lk.ijse.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class PetTm {

    private String id;
    private String name;
    private String breed;
    private double weight;
    private String colour;

}
