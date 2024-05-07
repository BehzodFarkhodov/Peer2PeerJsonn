package uz.pdp.model;

import lombok.*;
import uz.pdp.enumerator.Category;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Card extends BaseModel{

    private UUID ownerId;
    private  String cardNumber;
    private Category category;
    private double balance;
    private boolean isActive = true;

}
