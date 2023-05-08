package org.exchange.service;

import org.exchange.domain.dto.PrivatBankExchangeRateDTO;

import java.util.List;

public interface PrivatBankService {

    List<PrivatBankExchangeRateDTO> getAllExchangeRate();
}
