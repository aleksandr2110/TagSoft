package org.exchange.repository;

import org.exchange.domain.InterbankArchiveExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MinfinArchiveRepository extends JpaRepository<InterbankArchiveExchangeRate, String> {

    @Query(name = InterbankArchiveExchangeRate.FETCH_EXCHANGE_RATE_BY_DATE_QUERY_NAME)
    List<InterbankArchiveExchangeRate> fetchAllByDate(String date);
}
