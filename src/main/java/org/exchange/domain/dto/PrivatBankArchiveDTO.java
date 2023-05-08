package org.exchange.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PrivatBankArchiveDTO {
    @JsonProperty("baseCurrency")
    private String baseCurrency;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("saleRateNB")
    private Double saleRateNB;
    @JsonProperty("purchaseRateNB")
    private Double purchaseRateNB;
    @JsonProperty("saleRate")
    private Double saleRate;
    @JsonProperty("purchaseRate")
    private Double purchaseRate;
}
