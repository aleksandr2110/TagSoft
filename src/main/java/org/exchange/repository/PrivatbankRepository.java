package org.exchange.repository;

import org.exchange.domain.PrivatBankExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivatbankRepository extends JpaRepository<PrivatBankExchangeRate, Long> {
}
