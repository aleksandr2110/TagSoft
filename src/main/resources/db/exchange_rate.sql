create table monobank
(
   current_id              BIGINT PRIMARY KEY,
   current_code_a          BIGINT not null,
   current_code_b          BIGINT not null,
   date_modified           BIGINT not null,
   rate_buy                VARCHAR(255),
   rate_cross              VARCHAR(255),
   rate_cell               VARCHAR(255)
);
CREATE SEQUENCE current_id_seq START WITH 1 INCREMENT BY 1;

create table privat_bank
(
   currency_id             BIGINT PRIMARY KEY,
   ccy                     VARCHAR(255),
   base_ccy                VARCHAR(255),
   buy                     VARCHAR(255),
   sale                    VARCHAR(255)
);
CREATE SEQUENCE privat_bank_id_seq START WITH 1 INCREMENT BY 1;

create table privat_bank_archive_currency
(
   id                      INT PRIMARY KEY,
   date_archive            VARCHAR(255),
   bank                    VARCHAR(255),
   base_currency           INT,
   base_currency_lit       VARCHAR(255)
);
CREATE SEQUENCE privat_bank_date_id_seq START WITH 1 INCREMENT BY 1;

create table privat_bank_archive
(
   id                      INT PRIMARY KEY,
   exchange_rate_id        INT,
   base_currency           VARCHAR(255),
   currency                VARCHAR(255),
   sale_rate_nb            DECIMAL(5, 2),
   purchase_rate_nb        DECIMAL(5, 2),
   sale_rate               DECIMAL(5, 2),
   purchase_rate           DECIMAL(5, 2),
   FOREIGN KEY (exchange_rate_id) REFERENCES privat_bank_archive_currency(id)
);
CREATE SEQUENCE privat_bank_archive_date_id_seq START WITH 1 INCREMENT BY 1;

create table minfin_interbank
(
   id                      VARCHAR(255) PRIMARY KEY,
   point_date              VARCHAR(255),
   date_modified           VARCHAR(255),
   ask                     VARCHAR(255),
   bid                     VARCHAR(255),
   trend_ask               VARCHAR(255),
   trend_bid               VARCHAR(255),
   currency                VARCHAR(255)
);

create table minfin_interbank_archive_currency
(
   id                      INT PRIMARY KEY,
   archive_date            VARCHAR(255),
   currency                VARCHAR(255),
   ask                     VARCHAR(255),
   bid                     VARCHAR(255),
   trend_ask               VARCHAR(255),
   trend_bid               VARCHAR(255)
);
CREATE SEQUENCE interbank_archive_currency_id_seq START WITH 1 INCREMENT BY 1;












