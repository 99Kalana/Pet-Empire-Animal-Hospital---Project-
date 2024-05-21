package lk.ijse.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class ResetPassword {

    private String userId;
    private String name;
    private String password;

}
