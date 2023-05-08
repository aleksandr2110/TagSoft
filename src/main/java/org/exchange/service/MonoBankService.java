package org.exchange.service;

import org.exchange.domain.dto.MonobankExchangeRateDTO;

import java.util.List;

public interface MonoBankService {

    List<MonobankExchangeRateDTO> getAllExchangeRate();
}
