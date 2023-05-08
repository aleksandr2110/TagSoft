
package org.exchange.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "privat_bank_archive")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQuery(
        name = PrivatBankArchiveEntity.FETCH_ALL_EXCHANGE_RATE_BY_EXCHANGE_RATE_QUERY_NAME,
        query = PrivatBankArchiveEntity.FETCH_ALL_EXCHANGE_RATE_BY_EXCHANGE_RATE
)
public class PrivatBankArchiveEntity {

    public static final String FETCH_ALL_EXCHANGE_RATE_BY_EXCHANGE_RATE_QUERY_NAME = "PrivatBankArchiveEntity.fetchAllByExchangeId";
    public static final String FETCH_ALL_EXCHANGE_RATE_BY_EXCHANGE_RATE = "SELECT exchangeRateEntity FROM PrivatBankArchiveEntity " +
            "AS exchangeRateEntity WHERE exchangeRateId = :exchangeRateId";
    @Id
    @SequenceGenerator(name = "privat_bank_archive_date_id_seq", sequenceName = "privat_bank_archive_date_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "privat_bank_archive_date_id_seq")
    private Integer id;

    @Column(name = "exchange_rate_id", insertable = false, updatable = false)
    private Integer exchangeRateId;
    @Column(name = "base_currency")
    private String baseCurrency;
    @Column(name = "currency")
    private String currency;
    @Column(name = "sale_rate_nb")
    private Double saleRateNB;
    @Column(name = "purchase_rate_nb")
    private Double purchaseRateNB;
    @Column(name = "sale_rate")
    private Double saleRate;
    @Column(name = "purchase_rate")
    private Double purchaseRate;
    @ManyToOne
    @JoinColumn(name = "exchange_rate_id", nullable=false)
    private PrivatBankArchiveExchangeRate privatBankArchiveExchangeRate;
}
