package org.exchange.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PrivatBankExchangeRateDTO {
    @JsonProperty("ccy")
    private String ccy;

    @JsonProperty("base_ccy")
    private String baseCcy;

    @JsonProperty("buy")
    private String buy;

    @JsonProperty("sale")
    private String sale;
}
