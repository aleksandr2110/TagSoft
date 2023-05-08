package org.exchange.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PrivatBankArchiveExchangeRateDTO {
    @JsonProperty("date")
    private String date;

    @JsonProperty("bank")
    private String bank;

    @JsonProperty("baseCurrency")
    private Integer baseCurrency;

    @JsonProperty("baseCurrencyLit")
    private String baseCurrencyLit;

    @JsonProperty("exchangeRate")
    private List<PrivatBankArchiveDTO> exchangeRate = new ArrayList<>();
}
