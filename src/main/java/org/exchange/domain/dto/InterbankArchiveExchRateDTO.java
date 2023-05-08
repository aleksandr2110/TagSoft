package org.exchange.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class InterbankArchiveExchRateDTO {

    @JsonProperty("usd")
    private InterbankArchiveDTO usd;
    @JsonProperty("eur")
    private InterbankArchiveDTO eur;
    @JsonProperty("rub")
    private InterbankArchiveDTO rub;
    @JsonProperty("gbp")
    private InterbankArchiveDTO gbp;
    @JsonProperty("pln")
    private InterbankArchiveDTO pln;
    @JsonProperty("chf")
    private InterbankArchiveDTO chf;
    @JsonProperty("czk")
    private InterbankArchiveDTO czk;
    @JsonProperty("cad")
    private InterbankArchiveDTO cad;
    @JsonProperty("huf")
    private InterbankArchiveDTO huf;
    //private List<InterbankArchiveDTO> currenciesList = Arrays.asList(usd, eur, rub, gbp, pln, chf, czk, cad, huf);
    private List<String> currencies = Arrays.asList("usd", "eur", "rub", "gbp", "pln", "chf", "czk", "cad", "huf");

}
