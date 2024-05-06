package uz.pdp.model;

import lombok.*;


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
