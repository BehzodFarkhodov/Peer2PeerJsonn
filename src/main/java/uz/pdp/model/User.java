package uz.pdp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import uz.pdp.enumerator.Role;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseModel {

    private String name;

    private String username;

    private String password;
    private Role role;



}
