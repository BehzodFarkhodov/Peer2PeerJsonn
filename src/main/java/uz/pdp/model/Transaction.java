package uz.pdp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter

@AllArgsConstructor

public class Transaction extends BaseModel {
    private UUID fromCard;
    private UUID toCard;
    private double amount;
    private Double calculatedCommission;
    private UUID ownerId;


    @Override
    public String toString() {
        return "TRANSACTION --> | " +
                "FROMCARD = " + fromCard +
                ", TOCARD = " + toCard +
                ", AMOUNT = " + amount +
                '|';
    }
}
