package org.exchange.service;


import org.exchange.domain.dto.UnitedArchiveExchangeRateDTO;
import org.exchange.domain.dto.UnitedExchangeRateDTO;

public interface ExchangeRateService {

    UnitedExchangeRateDTO getAllExchangeRate();
    UnitedArchiveExchangeRateDTO getArchiveByDate(String date);
}
