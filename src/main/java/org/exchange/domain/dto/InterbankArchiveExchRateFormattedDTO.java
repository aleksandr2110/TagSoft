package org.exchange.domain.dto;

import lombok.Data;

@Data
public class InterbankArchiveExchRateFormattedDTO {

    private String archiveDate;
    private String currency;
    private String ask;
    private String bid;
    private Double trendAsk;
    private Double trendBid;
}
