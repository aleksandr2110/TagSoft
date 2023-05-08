package org.exchange.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "minfin_interbank_archive_currency")
@NamedQuery(
        name = InterbankArchiveExchangeRate.FETCH_EXCHANGE_RATE_BY_DATE_QUERY_NAME,
        query = InterbankArchiveExchangeRate.FETCH_EXCHANGE_RATE_BY_DATE
)
public class InterbankArchiveExchangeRate {

    public static final String FETCH_EXCHANGE_RATE_BY_DATE_QUERY_NAME = "InterbankArchiveExchangeRate.fetchAllByDate";
    public static final String FETCH_EXCHANGE_RATE_BY_DATE = "SELECT DISTINCT exchangeRate FROM InterbankArchiveExchangeRate " +
            "AS exchangeRate WHERE archiveDate = :date";
    @Id
    @SequenceGenerator(name = "interbank_archive_currency_id_seq", sequenceName = "interbank_archive_currency_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "interbank_archive_currency_id_seq")
    private Integer id;

    @Column(name = "archive_date")
    private String archiveDate;

    @Column(name = "currency")
    private String currency;

    @Column(name = "ask")
    private String ask;
    @Column(name = "bid")
    private String bid;
    @Column(name = "trend_ask")
    private String trendAsk;
    @Column(name = "trend_bid")
    private String trendBid;
}
