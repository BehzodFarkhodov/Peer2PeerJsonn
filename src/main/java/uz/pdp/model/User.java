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


    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
}
