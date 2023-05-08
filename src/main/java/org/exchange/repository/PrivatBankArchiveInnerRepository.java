package org.exchange.repository;

import org.exchange.domain.PrivatBankArchiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivatBankArchiveInnerRepository  extends JpaRepository<PrivatBankArchiveEntity, Integer> {

    @Query(name = PrivatBankArchiveEntity.FETCH_ALL_EXCHANGE_RATE_BY_EXCHANGE_RATE_QUERY_NAME)
    List<PrivatBankArchiveEntity> fetchAllByExchangeRateId(Integer exchangeRateId);
}
