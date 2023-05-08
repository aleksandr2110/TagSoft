package org.exchange.repository;

import org.exchange.domain.PrivatBankArchiveExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivatBankArchiveRepository extends JpaRepository<PrivatBankArchiveExchangeRate, Integer> {

    @Query(name = PrivatBankArchiveExchangeRate.FETCH_EXCHANGE_RATE_BY_DATE_QUERY_NAME)
    Optional<PrivatBankArchiveExchangeRate> fetchAllByDate(String date);
}
