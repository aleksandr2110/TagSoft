package org.exchange.repository;

import org.exchange.domain.InterbankExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinfinRepository extends JpaRepository<InterbankExchangeRate, String>  {
}
