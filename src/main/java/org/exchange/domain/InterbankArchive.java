package org.exchange.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "minfin_interbank_archive")
public class InterbankArchive {
    @Id
    @SequenceGenerator(name = "minfin_interbank_archive_id_seq", sequenceName = "minfin_interbank_archive_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "minfin_interbank_archive_id_seq")
    private Integer id;

    @Column(name = "currency")
    private String currency;
    @Column(name = "archive_date", insertable = false, updatable = false)
    private String archiveDate;
    @Column(name = "ask")
    private String ask;
    @Column(name = "bid")
    private String bid;
    @Column(name = "trend_ask")
    private String trendAsk;
    @Column(name = "trend_bid")
    private String trendBid;

    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "archive_date", referencedColumnName = "point_date")
    @ManyToOne
    @JoinColumn(name="archive_date", nullable = false)
    private InterbankArchiveExchangeRate interbankArchiveExchangeRate;
}
