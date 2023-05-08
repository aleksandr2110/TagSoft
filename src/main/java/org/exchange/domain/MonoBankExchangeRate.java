package org.exchange.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "monobank")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MonoBankExchangeRate {

    @Id
    @SequenceGenerator(name = "current_id_seq", sequenceName = "current_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "current_id_seq")
    private Integer currentId;

    @Column(name = "current_code_a")
    private Integer currencyCodeA;
    @Column(name = "current_code_b")
    private Integer currencyCodeB;
    @Column(name = "date_modified")
    private Integer date;
    @Column(name = "rate_buy")
    private String rateBuy;
    @Column(name = "rate_cross")
    private String rateCross;
    @Column(name = "rate_cell")
    private String rateSell;
}
