package org.exchange.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "privat_bank_archive_currency")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQuery(
        name = PrivatBankArchiveExchangeRate.FETCH_EXCHANGE_RATE_BY_DATE_QUERY_NAME,
        query = PrivatBankArchiveExchangeRate.FETCH_EXCHANGE_RATE_BY_DATE
)
public class PrivatBankArchiveExchangeRate {

    public static final String FETCH_EXCHANGE_RATE_BY_DATE_QUERY_NAME = "PrivatBankArchiveExchangeRate.fetchByDate";
    public static final String FETCH_EXCHANGE_RATE_BY_DATE = "SELECT exchangeRate FROM PrivatBankArchiveExchangeRate " +
            "AS exchangeRate WHERE date = :date";
    @Id
    @SequenceGenerator(name = "privat_bank_date_id_seq", sequenceName = "privat_bank_date_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "privat_bank_date_id_seq")
    private Integer id;

    @Column(name = "date_archive")
    private String date;
    @Column(name = "bank")
    private String bank;
    @Column(name = "base_currency")
    private Integer baseCurrency;
    @Column(name = "base_currency_lit")
    private String baseCurrencyLit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "privatBankArchiveExchangeRate")
    private List<PrivatBankArchiveEntity> privatBankArchive;
}
