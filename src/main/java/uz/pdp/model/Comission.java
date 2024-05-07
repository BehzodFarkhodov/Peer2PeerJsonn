package uz.pdp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Comission extends BaseModel{
    private UUID fromCard;
    private UUID toCard;
    private double percentage;
}


