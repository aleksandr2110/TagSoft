package org.exchange.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UnitedExchangeRateDTO {

    @JsonProperty("averageBuyUsd")
    private String averageBuyUsd;

    @JsonProperty("averageSellUsd")
    private String averageSellUsd;

    @JsonProperty("averageBuyEuro")
    private String averageBuyEuro;

    @JsonProperty("averageSellEuro")
    private String averageSellEuro;
    @JsonProperty("monobank")
    private List<MonobankExchangeRateDTO> monobankExchListDto;
    @JsonProperty("privatBank")
    private List<PrivatBankExchangeRateDTO> privatBankExcheRatetListDto;
    @JsonProperty("minfinInterbank")
    private List<InterbankExchangeRateDTO> interbankExchangeRateDtoList;
}
