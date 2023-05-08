package org.exchange.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MonobankExchangeRateDTO {

    @JsonProperty("currencyCodeA")
    private Integer currencyCodeA;

    @JsonProperty("currencyCodeB")
    private Integer currencyCodeB;

    @JsonProperty("date")
    private Integer date;

    @JsonProperty("rateBuy")
    private String rateBuy;

    @JsonProperty("rateCross")
    private String rateCross;

    @JsonProperty("rateSell")
    private String rateSell;
}
