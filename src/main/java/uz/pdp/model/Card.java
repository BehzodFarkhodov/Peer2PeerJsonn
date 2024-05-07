package uz.pdp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import uz.pdp.enumerator.Category;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Card extends BaseModel{

    private UUID ownerId;
    private  String cardNumber;
    private Category category;
    private double balance;
    private boolean isActive = true;

}
