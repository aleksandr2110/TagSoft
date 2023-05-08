package org.exchange.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "privat_bank")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PrivatBankExchangeRate {

    @Id
    @SequenceGenerator(name = "privat_bank_id_seq", sequenceName = "privat_bank_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "privat_bank_id_seq")
    private Long currencyId;

    @Column(name = "ccy")
    private String ccy;
    @Column(name = "base_ccy")
    private String baseCcy;
    @Column(name = "buy")
    private String buy;
    @Column(name = "sale")
    private String sale;
}
