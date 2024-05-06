package uz.pdp.model;

import lombok.*;
import uz.pdp.enumerator.Role;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User extends BaseModel {

    private String name;

    private String username;

    private String password;
    private Role role;



}
