package org.exchange.service;

import org.exchange.domain.dto.InterbankExchangeRateDTO;

import java.util.List;

public interface InterbankService {

    List<InterbankExchangeRateDTO> getAllExchangeRate();
}
