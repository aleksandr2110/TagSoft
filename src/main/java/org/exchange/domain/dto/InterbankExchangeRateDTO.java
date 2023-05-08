package org.exchange.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InterbankExchangeRateDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("pointDate")
    private String pointDate;

    @JsonProperty("date")
    private String date;

    @JsonProperty("ask")
    private String ask;

    @JsonProperty("bid")
    private String bid;

    @JsonProperty("trendAsk")
    private String trendAsk;

    @JsonProperty("trendBid")
    private String trendBid;

    @JsonProperty("currency")
    private String currency;
}
