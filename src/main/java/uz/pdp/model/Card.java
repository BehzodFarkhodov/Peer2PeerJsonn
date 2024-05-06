package uz.pdp.model;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Card extends BaseModel{

    private UUID ownerId;

    private UUID cardId;

    private  Integer cardPassword;


}
