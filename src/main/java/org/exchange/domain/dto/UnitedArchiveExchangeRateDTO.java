package org.exchange.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class UnitedArchiveExchangeRateDTO {

    @JsonProperty("dateArchive")
    private String dateArchive;
    @JsonProperty("averageArchExcRate")
    Map<String, Map<Double, Double>> averageArchExcRate;
}
