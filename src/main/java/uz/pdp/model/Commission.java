package uz.pdp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.enumerator.Category;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Commission extends BaseModel{
    private Category fromCard;
    private Category toCard;
    private Double percentage;
}


