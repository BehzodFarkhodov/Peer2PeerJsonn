package uz.pdp.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bank {

    @JsonProperty("Ccy")
    private String ccy;

    @JsonProperty("Rate")
    private Double rate;

}
