package org.exchange.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "minfin_interbank")
public class InterbankExchangeRate {

    @Id
    private String id;

    @Column(name = "point_date")
    private String pointDate;
    @Column(name = "date_modified")
    private String date;
    @Column(name = "ask")
    private String ask;
    @Column(name = "bid")
    private String bid;
    @Column(name = "trend_ask")
    private String trendAsk;
    @Column(name = "trend_bid")
    private String trendBid;
    @Column(name = "currency")
    private String currency;
}
