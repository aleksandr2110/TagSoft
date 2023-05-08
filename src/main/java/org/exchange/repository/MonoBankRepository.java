package org.exchange.repository;

import org.exchange.domain.MonoBankExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonoBankRepository extends JpaRepository<MonoBankExchangeRate, Integer> {
}
