package org.exchange.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InterbankArchiveDTO {

    @JsonProperty("bid")
    private String bid;
    @JsonProperty("ask")
    private String ask;

    @JsonProperty("trendAsk")
    private Double trendAsk;
    @JsonProperty("trendBid")
    private Double trendBid;
}
